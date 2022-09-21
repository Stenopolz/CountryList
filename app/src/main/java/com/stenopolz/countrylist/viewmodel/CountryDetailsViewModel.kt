package com.stenopolz.countrylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stenopolz.countrylist.extensions.CallResult
import com.stenopolz.countrylist.model.data.application.CountryFullInfo
import com.stenopolz.countrylist.model.data.application.DispatchersHolder
import com.stenopolz.countrylist.model.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val dispatchers: DispatchersHolder
) : ViewModel() {
    private val _countryInfo = MutableStateFlow<CountryFullInfo?>(null)
    val countryInfo: StateFlow<CountryFullInfo?> = _countryInfo

    private val _contentVisible = MutableStateFlow(false)
    val contentVisible: StateFlow<Boolean> = _contentVisible

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    private var countryId = ""

    fun fetchCountryDetails(id: String) {
        countryId = id
        viewModelScope.launch {
            handleLoading()
            withContext(dispatchers.io) {
                when (val result = countryRepository.fetchCountryDetails(id)) {
                    is CallResult.Success -> {
                        handleSuccess(result.data)
                    }
                    is CallResult.Error -> {
                        handleError()
                    }
                }
            }
        }
    }

    fun onTryAgain() {
        fetchCountryDetails(countryId)
    }

    private fun handleLoading() {
        _contentVisible.value = false
        _isError.value = false
        _isLoading.value = true
    }

    private fun handleSuccess(country: CountryFullInfo) {
        _countryInfo.tryEmit(country)
        _contentVisible.value = true
        _isError.value = false
        _isLoading.value = false
    }

    private fun handleError() {
        _contentVisible.value = false
        _isError.value = true
        _isLoading.value = false
    }
}
