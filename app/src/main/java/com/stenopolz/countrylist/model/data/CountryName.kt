package com.stenopolz.countrylist.model.data

import com.squareup.moshi.Json

data class CountryName(
    @field:Json(name = "official")
    val official: String
)