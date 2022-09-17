package com.stenopolz.countrylist.model.service

import com.stenopolz.countrylist.model.data.Country
import retrofit2.http.GET

interface CountryService {

    @GET("all")
    suspend fun getAllCountries(): List<Country>
}