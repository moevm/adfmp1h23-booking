package com.etu.booking.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DocumentViewModel : ViewModel() {
    private val _needToShowPermission = MutableStateFlow(true)

    val needToShowPermission: StateFlow<Boolean> = _needToShowPermission.asStateFlow()

    fun seePermission() {
        _needToShowPermission.value = false
    }
}
