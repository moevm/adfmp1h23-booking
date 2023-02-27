package com.etu.booking.view

import androidx.lifecycle.ViewModel
import com.etu.booking.messaging.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DocumentViewModel(
    private val snackbarManager: SnackbarManager,
) : ViewModel() {
    private val _needToShowPermission = MutableStateFlow(true)

    val needToShowPermission: StateFlow<Boolean> = _needToShowPermission.asStateFlow()

    fun seePermission() {
        _needToShowPermission.value = false
    }
}
