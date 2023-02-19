package com.etu.booking.view

import androidx.lifecycle.ViewModel
import com.etu.booking.model.AuthorizationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthorizationViewModel : ViewModel() {
    private val _authorizationState = MutableStateFlow(AuthorizationModel())

    val authorizationState: StateFlow<AuthorizationModel> = _authorizationState.asStateFlow()

    fun logIn() {
        _authorizationState.compareAndSet(
            expect = AuthorizationModel(isAuthorized = false),
            update = AuthorizationModel(isAuthorized = true)
        )
    }

    fun logOut() {
        _authorizationState.compareAndSet(
            expect = AuthorizationModel(isAuthorized = true),
            update = AuthorizationModel(isAuthorized = false)
        )
    }
}