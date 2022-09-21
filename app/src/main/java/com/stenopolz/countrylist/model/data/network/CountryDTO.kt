package com.stenopolz.countrylist.model.data.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDTO(
    @field:Json(name = "name")
    val name: CountryNameDTO,
    @field:Json(name = "capital")
    val capital: List<String>?,
    @field:Json(name = "region")
    val region: String,
    @field:Json(name = "subregion")
    val subRegion: String?,
    @field:Json(name = "flags")
    val flags: FlagsDTO,
    @field:Json(name = "cca3")
    val id: String,
    @field:Json(name = "area")
    val area: Double,
    @field:Json(name = "currencies")
    val currencies: Map<String, CurrencyDTO>?
)
