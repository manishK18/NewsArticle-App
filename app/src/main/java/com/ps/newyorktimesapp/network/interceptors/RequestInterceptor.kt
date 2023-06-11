package com.ps.newyorktimesapp.network.interceptors

import com.ps.newyorktimesapp.constants.NetworkConstants
import com.ps.newyorktimesapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url().newBuilder()
            .addQueryParameter(NetworkConstants.KEY_API_KEY, BuildConfig.NewYorkTimesApiKey)
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}