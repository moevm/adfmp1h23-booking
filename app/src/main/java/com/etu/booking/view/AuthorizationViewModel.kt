package com.etu.booking.view

import android.util.Log
import androidx.lifecycle.ViewModel
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
        Log.d("AuthorizationStateChange", "LogOut: ${_authorizationState.value}")
    }

    fun logOut() {
        _authorizationState.update { it.copy(isAuthorized = false) }
        Log.d("AuthorizationStateChange", "LogOut${_authorizationState.value}")
    }
}
