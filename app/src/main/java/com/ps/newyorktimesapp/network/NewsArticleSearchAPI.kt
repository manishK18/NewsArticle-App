package com.ps.newyorktimesapp.network

import com.ps.newyorktimesapp.models.SearchArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsArticleSearchAPI {
    @GET("news/search")
    suspend fun getNewsArticle(
        @QueryMap(encoded = true) requestMap: Map<String, String>
    ): Response<SearchArticleResponse>

    companion object {
        fun create(): NewsArticleSearchAPI =
            RetrofitManager().createRetrofitService(NewsArticleSearchAPI::class.java)
    }
}
