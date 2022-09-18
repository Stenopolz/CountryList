package com.stenopolz.countrylist.model.service

import com.stenopolz.countrylist.model.data.network.CountryDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {

    @GET("all")
    suspend fun getAllCountries(): List<CountryDTO>

    @GET("alpha/{id}")
    suspend fun getCountry(@Path("id") id: String): List<CountryDTO>
}
