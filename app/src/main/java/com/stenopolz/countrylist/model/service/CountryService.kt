package com.stenopolz.countrylist.model.service

import com.stenopolz.countrylist.model.data.network.CountryDTO
import javax.inject.Inject

class CountryService @Inject constructor(
    private val countryApi: CountryApi
) {
    suspend fun getAllCountries(): List<CountryDTO> {
        return countryApi.getAllCountries()
    }

    suspend fun getCountry(code: String): List<CountryDTO> {
        return countryApi.getCountry(code)
    }
}
