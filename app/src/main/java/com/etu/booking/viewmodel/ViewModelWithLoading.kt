package com.etu.booking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ViewModelWithLoading : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    val isLoading = _isLoading.asStateFlow()

    protected fun launchWithLoading(block: suspend () -> Unit) = viewModelScope.launch {
        _isLoading.update { true }
        delay(1000) // TODO: Only for request delay emulation. It will be removed
        block()
        _isLoading.update { false }
    }
}
