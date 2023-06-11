package com.ps.newyorktimesapp.repo

import com.ps.newyorktimesapp.database.dao.NewsArticleDao
import com.ps.newyorktimesapp.database.models.QueryCache
import com.ps.newyorktimesapp.database.models.QueryCacheNewsArticleCrossRef
import com.ps.newyorktimesapp.models.ArticleData
import com.ps.newyorktimesapp.models.SearchArticleResponse
import com.ps.newyorktimesapp.network.NewsArticleSearchAPI
import com.ps.newyorktimesapp.network.RequestResult
import com.ps.newyorktimesapp.utils.PreferenceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SearchNewsFragmentRepo(
    private val api: NewsArticleSearchAPI,
    private val localDataSource: NewsArticleDao?,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository(coroutineDispatcher) {

    suspend fun getSearchArticlesNYTAPI(
        query: String,
        offset: Int,
        limit: Int
    ): Result<SearchArticleResponse> {
        if (PreferenceManager.isAppModeOnline().not()) {
            return Result.success(
                serveCacheResponseData(query)
            )
        }
        val apiResult = createApiCall {
            api.getNewsArticle(query, offset, limit)
        }
        if (apiResult.isSuccess) {
            // Cache data
            cacheNewsWithQueryData(
                query = query,
                articleDataList = apiResult.getOrNull()?.articleDataList
            )
            return apiResult
        } else {
            val newsArticleList = mutableListOf<ArticleData>()
            localDataSource?.getNewsArticles(query)?.forEach {
                newsArticleList.addAll(it.newsArticles)
            }
            if (newsArticleList.isNotEmpty()) {
                return Result.success(
                    SearchArticleResponse(
                        query = query,
                        articleDataList = newsArticleList,
                        status = RequestResult.Status.SUCCESS.toString(),
                        meta = null,
                        currentPageNum = 0,
                        nextPageNum = 0,
                        previousPageNum = 0
                    )
                )
            } else {
                return Result.failure(
                    exception = apiResult.exceptionOrNull()
                        ?: Throwable("Api not reachable, also no cache data found")
                )
            }
        }
    }

//    suspend fun getSearchArticles(query: String, pageNum: String): Result<SearchArticleResponse> {
//        return createApiCall {
//            api.getLocalSearchArticles(query, pageNum)
//        }
//    }

    private suspend fun serveCacheResponseData(query: String): SearchArticleResponse {
        val newsArticleList = mutableListOf<ArticleData>()
        localDataSource?.getNewsArticles(query)?.forEach {
            newsArticleList.addAll(it.newsArticles)
        }
        return SearchArticleResponse(
            articleDataList = newsArticleList,
            status = RequestResult.Status.SUCCESS.toString(),
            query = query,
            currentPageNum = 0,
            nextPageNum = 0,
            previousPageNum = 0,
            meta = null
        )
    }

    private suspend fun cacheNewsWithQueryData(query: String, articleDataList: List<ArticleData>?) {
        articleDataList?.forEach {
            localDataSource?.insertNewsArticle(it)
            localDataSource?.insertQuery(
                queryCache = QueryCache(query = query)
            )
            localDataSource?.insertQueryWithNewsArticles(
                queryCacheNewsArticleCrossRef = QueryCacheNewsArticleCrossRef(
                    query = query,
                    id = it.id,
                    headline = it.headline ?: ""
                )
            )
        }
    }
}
