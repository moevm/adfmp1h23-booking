package com.etu.booking.viewmodel

import androidx.lifecycle.ViewModel
import com.etu.booking.R
import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.model.AuthorizationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthorizationViewModel(
    private val snackbarManager: SnackbarManager,
) : ViewModel() {
    private val _authorizationState = MutableStateFlow(AuthorizationModel())

    val authorizationState: StateFlow<AuthorizationModel> = _authorizationState.asStateFlow()

    fun logIn() {
        _authorizationState.update { it.copy(isAuthorized = true) }
        snackbarManager.showMessage(R.string.authorization_toggle_on)
    }

    fun logOut() {
        _authorizationState.update { it.copy(isAuthorized = false) }
        snackbarManager.showMessage(R.string.authorization_toggle_off)
    }
}
