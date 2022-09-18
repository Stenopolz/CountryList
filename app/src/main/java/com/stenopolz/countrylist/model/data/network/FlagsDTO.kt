package com.stenopolz.countrylist.model.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlagsDTO(
    @field:Json(name = "png")
    val pngImageUrl: String
)
