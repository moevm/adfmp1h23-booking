package com.etu.booking.compose.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.etu.booking.compose.dependecyinjection.DIService

@Composable
fun NavigationController(
    innerPadding: PaddingValues,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Search.route) { DIService.SearchScreen() }
        composable(Screen.History.route) { DIService.HistoryScreen() }
        composable(Screen.Profile.route) { DIService.ProfileScreen() }
        composable(Screen.More.route) { DIService.MoreScreen() }
    }
}
