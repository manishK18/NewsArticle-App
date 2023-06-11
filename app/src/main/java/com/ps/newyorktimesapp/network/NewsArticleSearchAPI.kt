package com.ps.newyorktimesapp.network

import com.ps.newyorktimesapp.models.SearchArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsArticleSearchAPI {
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
