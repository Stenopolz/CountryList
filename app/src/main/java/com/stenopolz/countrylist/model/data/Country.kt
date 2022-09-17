package com.stenopolz.countrylist.model.data

import com.squareup.moshi.Json

data class Country(
    @field:Json(name = "name")
    val name: CountryName
)