package com.ps.newyorktimesapp.network

import com.ps.newyorktimesapp.models.SearchArticleResponse
import com.ps.newyorktimesapp.models.api_direct.SearchArticlesResponseAPI
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsArticleSearchAPI {
//    @GET("svc/search/v2/articlesearch.json")
//    suspend fun getSearchArticles(
//        @Query("q") query: String,
//        @Query("page") pageNum: String
//    ): Response<SearchArticlesResponseAPI>

    @GET("news/search")
    suspend fun getNewsArticle(
        @Query("query") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<SearchArticleResponse>

    companion object {
        fun create(): NewsArticleSearchAPI =
            RetrofitManager().createRetrofitService(NewsArticleSearchAPI::class.java)
    }
}
