package com.priesniakov.redditviewercompose.data


sealed class Resource<out T>

data class Success<out T : Any>(val data: T) : Resource<T>()
data class ResError<E>(val message: String) : Resource<E>()
object Loading : Resource<Nothing>()