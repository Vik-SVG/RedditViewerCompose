package com.priesniakov.redditviewercompose.core.domain

import com.priesniakov.redditviewercompose.data.Loading
import com.priesniakov.redditviewercompose.data.ResError
import com.priesniakov.redditviewercompose.data.Resource
import com.priesniakov.redditviewercompose.data.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase {

    fun <R> performNetworkCall(
        networkCall: suspend () -> Resource<R>,
    ): Flow<Resource<R>> =
        flow {
            emit(Loading)
            val networkResponse = networkCall.invoke()
            if (networkResponse is Success) {
                emit(networkResponse)
            } else if (networkResponse is ResError){
                emit(ResError(networkResponse.message))
            }
        }.flowOn(Dispatchers.IO)

    fun <R> performNetworkCallWithCashing(
        saveData: suspend (data: R) -> Unit,
        networkCall: suspend () -> Resource<R>,
    ): Flow<Resource<R>> =
        flow {
            emit(Loading)

            val networkResponse = networkCall.invoke()

            if (networkResponse is Success) {
                emit(networkResponse)
                saveData(networkResponse.data)
            } else if (networkResponse is ResError)
                emit(ResError(networkResponse.message))
        }.flowOn(Dispatchers.IO)

    fun <R> getCashedData(getCash: suspend () -> R?): Flow<Resource<R>> =
        flow {
            emit(Loading)
            val cash = getCash()
            if (cash != null) {
                emit(Success(cash))
            } else {
                emit(ResError(message = "Entity not cashed"))
            }
        }.flowOn(Dispatchers.IO)

}