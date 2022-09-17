package com.stenopolz.countrylist.extensions

sealed class CallResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : CallResult<T>()
    data class Error(val exception: Exception) : CallResult<Nothing>()

    fun getDataOrNull(): T? {
        return if (this is Success) data else null
    }
}