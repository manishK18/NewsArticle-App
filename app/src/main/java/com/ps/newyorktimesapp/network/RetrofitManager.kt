package com.ps.newyorktimesapp.network

import com.ps.newyorktimesapp.constants.NetworkConstants
import com.ps.newyorktimesapp.network.interceptors.RequestInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    private var retrofit: Retrofit? = null

    private fun buildRetrofitClient() {
        retrofit = Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL_NEWS_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(RequestInterceptor())
                    .build()
            )
            .build()
    }

    fun <S> createRetrofitService(serviceClass: Class<S>): S {
        synchronized(RetrofitManager::class.java) {
            if (retrofit == null) {
                buildRetrofitClient()
            }
            return retrofit!!.create(serviceClass)
        }
    }
}
