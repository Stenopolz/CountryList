package com.stenopolz.countrylist.model.data.application

import kotlin.coroutines.CoroutineContext

data class DispatchersHolder(
    val main: CoroutineContext,
    val io: CoroutineContext,
    val default: CoroutineContext
)
