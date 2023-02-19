package com.etu.booking.compose.dependecyinjection

import androidx.compose.runtime.Composable
import com.etu.booking.compose.screen.HistoryScreen
import com.etu.booking.compose.screen.MoreScreen
import com.etu.booking.compose.screen.ProfileScreen
import com.etu.booking.view.AuthorizationViewModel

object DIService {

    private val authorizationViewModel = AuthorizationViewModel()

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
}