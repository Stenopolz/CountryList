package com.stenopolz.countrylist.viewmodel

import androidx.lifecycle.ViewModel
import com.stenopolz.countrylist.model.data.application.CountryShortInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor() : ViewModel() {

    fun showCountryDetails(country: CountryShortInfo) {

    }
}
