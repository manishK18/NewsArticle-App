package com.ps.newyorktimesapp.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // We can use this interceptor to handle the exception thrown while making request, log them and push to Jumbo, etc.
        // Keeping this for reference only.
        val url = chain.request().url().newBuilder()
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}