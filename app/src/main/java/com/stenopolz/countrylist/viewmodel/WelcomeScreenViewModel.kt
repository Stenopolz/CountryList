package com.stenopolz.countrylist.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor() : ViewModel() {

    private val _goToNextScreen = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val goToNextScreen: SharedFlow<Unit> = _goToNextScreen

    fun onGoToNext() {
        _goToNextScreen.tryEmit(Unit)
    }
}
