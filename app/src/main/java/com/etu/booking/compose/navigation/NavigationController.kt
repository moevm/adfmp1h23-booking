package com.etu.booking.compose.navigation

import DocumentScreen
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.etu.booking.compose.dependecyinjection.DIService
import com.etu.booking.compose.screen.AboutUsScreen
import com.etu.booking.compose.screen.AuthScreen
import com.etu.booking.compose.screen.BookingListScreen
import com.etu.booking.compose.screen.HistoryScreen
import com.etu.booking.compose.screen.HotelBookScreen
import com.etu.booking.compose.screen.HotelScreen
import com.etu.booking.compose.screen.MoreScreen
import com.etu.booking.compose.screen.ProfileScreen
import com.etu.booking.compose.screen.SearchScreen
import com.etu.booking.compose.screen.UnauthorizedScreen

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
        composable(Screen.Search.route) {
            SearchScreen(
                viewModel = DIService.bookingSearchViewModel,
                onSearch = { navController.navigate(Screen.BookingList.route) }
            )
        }
        composable(Screen.History.route) {
            ComposableOrUnauthorizedScreen(navController) {
                HistoryScreen()
            }
        }
        composable(Screen.Profile.route) {
            ComposableOrUnauthorizedScreen(navController) {
                ProfileScreen(
                    onAddDocumentClick = { Log.d("onClick", "Add document") },
                    onShowDocumentsClick = { navController.navigate(Screen.Document.route) },
                )
            }
        }
        composable(Screen.More.route) {
            MoreScreen(
                authorizationViewModel = DIService.authorizationViewModel,
                onAboutUsCardClick = { navController.navigate(Screen.AboutUs.route) }
            )
        }
        composable(Screen.AboutUs.route) { AboutUsScreen() }
        composable(Screen.Unauthorized.route) {
            UnauthorizedScreenWrapper(navController)
        }
        composable(Screen.BookingList.route) {
            BookingListScreen(
                bookingSearchViewModel = DIService.bookingSearchViewModel,
                onCardClick = { hotelId -> navController.navigate(Screen.Hotel.route + "/$hotelId") }
            )
        }
        composable(Screen.Auth.route) {
            AuthScreen(viewModel = DIService.authViewModel)
        }
        composable(
            route = Screen.Hotel.route + "/{$HOTEL_ID}",
            arguments = listOf(navArgument(HOTEL_ID) { type = NavType.StringType }),
        ) { entry ->
            val hotelId = entry.arguments?.getString(HOTEL_ID)!!
            HotelScreen(
                hotelId = hotelId,
                onBookNowClick = {
                    navController.navigate(Screen.HotelBookScreen.route + "/$hotelId")
                },
            )
        }
        composable(Screen.Document.route) {
            DocumentScreen()
        }
        composable(
            route = Screen.HotelBookScreen.route + "/{$HOTEL_ID}",
            arguments = listOf(navArgument(HOTEL_ID) { type = NavType.StringType }),
        ) { entry ->
            ComposableOrUnauthorizedScreen(navController) {
                HotelBookScreen(
                    bookingSearchViewModel = DIService.bookingSearchViewModel,
                    hotelId = entry.arguments?.getString(HOTEL_ID)!!,
                    onSuccessClick = { navController.navigate(Screen.Search.route) },
                    onFailedClick = { navController.navigate(Screen.Search.route) },
                )
            }
        }
    }
}

@Composable
private fun ComposableOrUnauthorizedScreen(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    val authorizationState = DIService.authorizationViewModel.authorizationState.collectAsState()
    val isNotAuthorized = !authorizationState.value.isAuthorized

    if (isNotAuthorized) {
        UnauthorizedScreenWrapper(navController)
    } else {
        content()
    }
}

@Composable
private fun UnauthorizedScreenWrapper(navController: NavHostController) {
    UnauthorizedScreen(
        onStartSignIn = { navController.navigate(Screen.Auth.route) }
    )
}

private const val HOTEL_ID = "hotelId"