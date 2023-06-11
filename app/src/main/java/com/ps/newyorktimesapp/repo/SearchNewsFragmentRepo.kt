package com.ps.newyorktimesapp.repo

import com.ps.newyorktimesapp.constants.RequestParamConstants
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
        requestMap: HashMap<String, String>
    ): Result<SearchArticleResponse> {
        val query = requestMap[RequestParamConstants.KEY_REQUEST_PARAM_QUERY] ?: ""
        if (PreferenceManager.isAppModeOnline().not()) {
            return Result.success(
                serveCacheResponseData(query)
            )
        }
        val apiResult = createApiCall {
            api.getNewsArticle(requestMap)
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
            return if (newsArticleList.isNotEmpty()) {
                Result.success(
                    SearchArticleResponse(
                        query = query,
                        articleDataList = newsArticleList,
                        status = RequestResult.Status.SUCCESS.toString()
                    )
                )
            } else {
                Result.failure(
                    exception = apiResult.exceptionOrNull()
                        ?: Throwable("Api not reachable, also no cache data found")
                )
            }
        }
    }

    private suspend fun serveCacheResponseData(query: String): SearchArticleResponse {
        val newsArticleList = mutableListOf<ArticleData>()
        localDataSource?.getNewsArticles(query)?.forEach {
            newsArticleList.addAll(it.newsArticles)
        }
        return SearchArticleResponse(
            articleDataList = newsArticleList,
            status = RequestResult.Status.SUCCESS.toString(),
            query = query
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
