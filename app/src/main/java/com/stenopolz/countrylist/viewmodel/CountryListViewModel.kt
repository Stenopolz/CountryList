package com.stenopolz.countrylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stenopolz.countrylist.extensions.CallResult
import com.stenopolz.countrylist.model.data.application.CountryShortInfo
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
class CountryListViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val dispatchers: DispatchersHolder
) : ViewModel() {
    private val _countryList = MutableStateFlow<List<CountryShortInfo>>(emptyList())
    val countryList: StateFlow<List<CountryShortInfo>> = _countryList

    private val _contentVisible = MutableStateFlow(false)
    val contentVisible: StateFlow<Boolean> = _contentVisible

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    private val _countryIdSelected = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val countryIdSelected: SharedFlow<String> = _countryIdSelected

    init {
        fetchCountries()
    }

    fun onCountryIdClicked(id: String) {
        _countryIdSelected.tryEmit(id)
    }

    fun onTryAgain() {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            handleLoading()
            withContext(dispatchers.io) {
                when (val result = countryRepository.fetchCountryList()) {
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

    private fun handleLoading() {
        _contentVisible.value = false
        _isError.value = false
        _isLoading.value = true
    }

    private fun handleSuccess(countryList: List<CountryShortInfo>) {
        _countryList.value = countryList
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
