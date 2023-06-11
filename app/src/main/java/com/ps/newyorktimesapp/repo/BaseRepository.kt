package com.ps.newyorktimesapp.repo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

open class BaseRepository(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend fun <T : Any> createApiCall(call: suspend () -> Response<T>): Result<T> =
        withContext(coroutineDispatcher) {
            return@withContext createApiResult(call)
        }

    private suspend fun <T : Any> createApiResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call.invoke()
            println("API Call sent - ${response.raw().request()}")
            if (response.isSuccessful) {
                return Result.success(response.body()!!)
            }
            println(response.toString())
            return Result.failure(Exception(response.toString()))
        } catch (ex: Exception) {
            return Result.failure(ex)
        }
    }
}
