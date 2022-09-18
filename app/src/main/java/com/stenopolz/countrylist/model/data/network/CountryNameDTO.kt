package com.stenopolz.countrylist.model.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryNameDTO(
    @field:Json(name = "official")
    val official: String
)
