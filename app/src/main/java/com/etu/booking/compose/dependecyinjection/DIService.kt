package com.etu.booking.compose.dependecyinjection

import androidx.compose.runtime.Composable
import com.etu.booking.compose.screen.AuthScreen
import com.etu.booking.compose.screen.HistoryScreen
import com.etu.booking.compose.screen.MoreScreen
import com.etu.booking.compose.screen.ProfileScreen
import com.etu.booking.view.AuthViewModel
import com.etu.booking.view.AuthorizationViewModel

object DIService {

    private val authorizationViewModel = AuthorizationViewModel()
    private val authViewModel = AuthViewModel()

    @Composable
    fun HistoryScreen() {
        HistoryScreen(authorizationViewModel = authorizationViewModel)
    }

    @Composable
    fun ProfileScreen() {
        ProfileScreen(authorizationViewModel = authorizationViewModel)
    }

    @Composable
    fun MoreScreen() {
        MoreScreen(authorizationViewModel = authorizationViewModel)
    }

    @Composable
    fun AuthScreen() {
        AuthScreen(viewModel = authViewModel)
    }
}