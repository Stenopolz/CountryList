package com.stenopolz.countrylist.model.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyDTO(
    @field:Json(name = "name")
    val name: String,
)
