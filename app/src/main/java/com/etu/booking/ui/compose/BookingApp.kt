package com.etu.booking.ui.compose

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import com.etu.booking.ui.compose.navigation.BookingBottomNavigation
import com.etu.booking.ui.compose.navigation.NavigationController
import com.etu.booking.ui.theme.BookingTheme

@Composable
fun BookingApp() {
    BookingTheme {
        val appState = rememberBookingAppState()

        Scaffold(
            bottomBar = { BookingBottomNavigation(appState.navController) },
            snackbarHost = { SnackbarHost(hostState = it) },
            scaffoldState = appState.scaffoldState
        ) {
            NavigationController(innerPadding = it, navController = appState.navController)
        }
    }
}


