package com.priesniakov.redditviewercompose.core.data

import android.util.Log
import com.priesniakov.redditviewercompose.data.Resource
import com.priesniakov.redditviewercompose.data.ResError
import com.priesniakov.redditviewercompose.data.Success
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResults(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                Log.i("BaseDataSource", body.toString())
                if (body != null) return Success(body)
            }
            return error(" ${response.code()} ${response.message()} ")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d("BaseDataSource", message)
        return ResError("Network call has failed because of next reason: $message")
    }
}