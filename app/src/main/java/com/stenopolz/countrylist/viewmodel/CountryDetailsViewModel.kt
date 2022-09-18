package com.stenopolz.countrylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stenopolz.countrylist.extensions.CallResult
import com.stenopolz.countrylist.model.data.application.CountryFullInfo
import com.stenopolz.countrylist.model.data.application.DispatchersHolder
import com.stenopolz.countrylist.model.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val dispatchers: DispatchersHolder
): ViewModel() {
    private val _countryInfo = MutableLiveData<CountryFullInfo>()
    val countryInfo: LiveData<CountryFullInfo> = _countryInfo

    private val _contentVisible = MutableLiveData(false)
    val contentVisible: LiveData<Boolean> = _contentVisible

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

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
        _contentVisible.postValue(false)
        _isError.postValue(false)
        _isLoading.postValue(true)
    }

    private fun handleSuccess(country: CountryFullInfo) {
        _countryInfo.postValue(country)
        _contentVisible.postValue(true)
        _isError.postValue(false)
        _isLoading.postValue(false)
    }

    private fun handleError() {
        _contentVisible.postValue(false)
        _isError.postValue(true)
        _isLoading.postValue(false)
    }
}
