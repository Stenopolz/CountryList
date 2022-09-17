package com.stenopolz.countrylist.extensions

suspend fun <T : Any> apiCall(call: suspend (() -> T?)): CallResult<T> {
    return try {
        // We need to unbox the call() to make sure that the data is not null
        call()?.let {
            CallResult.Success(it)
        } ?: run {
            CallResult.Error(IllegalArgumentException("The data was null"))
        }
    } catch (e: Exception) {
        CallResult.Error(e)
    }
}