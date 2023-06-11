package com.ps.newyorktimesapp.repo

import com.ps.newyorktimesapp.database.dao.NewsArticleDao
import com.ps.newyorktimesapp.database.models.NewsArticle
import com.ps.newyorktimesapp.database.models.QueryCache
import com.ps.newyorktimesapp.database.models.QueryCacheNewsArticleCrossRef
import com.ps.newyorktimesapp.models.SearchArticleResponse
import com.ps.newyorktimesapp.models.api_direct.ArticleDataAPI
import com.ps.newyorktimesapp.models.api_direct.Response
import com.ps.newyorktimesapp.models.api_direct.SearchArticlesResponseAPI
import com.ps.newyorktimesapp.network.NewsArticleSearchAPI
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
        pageNum: String
    ): Result<SearchArticlesResponseAPI> {
        if (PreferenceManager.isAppModeOnline().not()) return Result.success(
            serveCacheResponseData(
                query
            )
        )
        val apiResult = createApiCall {
            api.getSearchArticles(query, pageNum)
        }
        if (apiResult.isSuccess) {
            // Cache data
            cacheNewsWithQueryData(
                query = query,
                response = apiResult.getOrNull()?.response
            )
            return apiResult
        } else {
            val newsArticleList = mutableListOf<ArticleDataAPI>()
            localDataSource?.getNewsArticles(query)?.forEach {
                it.newsArticles.map {
                    it.article?.let { article -> newsArticleList.add(article) }
                }
            }
            if (newsArticleList.isNotEmpty()) {
                return Result.success(
                    SearchArticlesResponseAPI(
                        response = Response(
                            articleDataList = newsArticleList,
                            meta = null,
                            currentPageNum = 0,
                            nextPageNum = 0,
                            previousPageNum = 0
                        ),
                        status = "OK"
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

    suspend fun getSearchArticles(query: String, pageNum: String): Result<SearchArticleResponse> {
        return createApiCall {
            api.getLocalSearchArticles(query, pageNum)
        }
    }

    private suspend fun serveCacheResponseData(query: String): SearchArticlesResponseAPI {
        val newsArticleList = mutableListOf<ArticleDataAPI>()
        localDataSource?.getNewsArticles(query)?.forEach {
            it.newsArticles.map {
                it.article?.let { article -> newsArticleList.add(article) }
            }
        }
        return SearchArticlesResponseAPI(
            response = Response(
                articleDataList = newsArticleList,
                meta = null,
                currentPageNum = 0,
                nextPageNum = 0,
                previousPageNum = 0
            ),
            status = "OK"
        )
    }

    private suspend fun cacheNewsWithQueryData(query: String, response: Response?) {
        response?.articleDataList?.forEach {
            localDataSource?.insertNewsArticle(
                NewsArticle(
                    newsId = it.id,
                    article = it
                )
            )
            localDataSource?.insertQuery(
                queryCache = QueryCache(
                    query = query
                )
            )
            localDataSource?.insertQueryWithNewsArticles(
                queryCacheNewsArticleCrossRef = QueryCacheNewsArticleCrossRef(
                    query = query,
                    newsId = it.id
                )
            )
        }
    }
}
