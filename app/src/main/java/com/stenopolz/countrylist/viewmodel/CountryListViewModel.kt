package com.stenopolz.countrylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stenopolz.countrylist.extensions.CallResult
import com.stenopolz.countrylist.model.data.application.CountryShortInfo
import com.stenopolz.countrylist.model.data.application.DispatchersHolder
import com.stenopolz.countrylist.model.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val dispatchers: DispatchersHolder
) : ViewModel() {
    private val _countryList = MutableLiveData<List<CountryShortInfo>>(emptyList())
    val countryList: LiveData<List<CountryShortInfo>> = _countryList

    private val _contentVisible = MutableLiveData(false)
    val contentVisible: LiveData<Boolean> = _contentVisible

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _countryIdSelected = MutableSharedFlow<String>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
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
        _contentVisible.postValue(false)
        _isError.postValue(false)
        _isLoading.postValue(true)
    }

    private fun handleSuccess(countryList: List<CountryShortInfo>) {
        _countryList.postValue(countryList)
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
