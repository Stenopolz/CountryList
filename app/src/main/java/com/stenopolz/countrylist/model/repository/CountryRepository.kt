package com.stenopolz.countrylist.model.repository

import com.stenopolz.countrylist.extensions.CallResult
import com.stenopolz.countrylist.extensions.apiCall
import com.stenopolz.countrylist.model.data.application.CountryFullInfo
import com.stenopolz.countrylist.model.data.application.CountryShortInfo
import com.stenopolz.countrylist.model.service.CountryService
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val service: CountryService
) {
    /**
     * @return list of all available countries
     */
    suspend fun fetchCountryList(): CallResult<List<CountryShortInfo>> {
        return apiCall {
            service.getAllCountries().map { country ->
                CountryShortInfo(
                    name = country.name.official,
                    capital = country.capital?.firstOrNull() ?: "n/a",
                    region = country.region,
                    flagUrl = country.flags.pngImageUrl,
                    cca3Code = country.cca3Code
                )
            }
        }
    }

    /**
     * @param code country cca3 code
     * @return first country that matches this code or error
     */
    suspend fun fetchCountryDetails(code: String): CallResult<CountryFullInfo> {
        return apiCall {
            service.getCountry(code).firstOrNull()?.let { country ->
                CountryFullInfo(
                    name = country.name.official,
                    capital = country.capital?.firstOrNull() ?: "n/a",
                    region = country.region,
                    flagUrl = country.flags.pngImageUrl,
                    cca3Code = country.cca3Code
                )
            }
        }
    }
}
