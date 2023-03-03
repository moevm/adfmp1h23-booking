package com.etu.booking.ui.compose.navigation

import DocumentScreen
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
import com.etu.booking.dependecyinjection.DIService
import com.etu.booking.ui.compose.screen.AboutUsScreen
import com.etu.booking.ui.compose.screen.AuthScreen
import com.etu.booking.ui.compose.screen.BookingListScreen
import com.etu.booking.ui.compose.screen.CameraScreen
import com.etu.booking.ui.compose.screen.HistoryScreen
import com.etu.booking.ui.compose.screen.HotelBookingScreen
import com.etu.booking.ui.compose.screen.HotelScreen
import com.etu.booking.ui.compose.screen.MoreScreen
import com.etu.booking.ui.compose.screen.ProfileScreen
import com.etu.booking.ui.compose.screen.SearchScreen
import com.etu.booking.ui.compose.screen.UnauthorizedScreen
import java.util.*

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
                HistoryScreen(historyViewModel = DIService.historyViewModel)
            }
        }
        composable(Screen.Profile.route) {
            ComposableOrUnauthorizedScreen(navController) {
                ProfileScreen(
                    profileViewModel = DIService.profileViewModel,
                    onAddDocumentClick = { navController.navigate(Screen.AddDocument.route) },
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
                onCardClick = { hotelId ->
                    navController.navigate(Screen.Hotel.route + "/$hotelId")
                }
            )
        }
        composable(Screen.Auth.route) {
            AuthScreen(
                authViewModel = DIService.authViewModel,
                profileViewModel = DIService.profileViewModel,
                authorizationViewModel = DIService.authorizationViewModel,
                onSignInClick = { navController.navigateUp() },
                onSignUpClick = { navController.navigateUp() },
                onAddDocumentClick = { navController.navigate(Screen.SignUpAddDocument.route) },
            )
        }
        composable(
            route = Screen.Hotel.route + "/{$HOTEL_ID}",
            arguments = listOf(navArgument(HOTEL_ID) { type = NavType.StringType }),
        ) { entry ->
            val hotelId = UUID.fromString(entry.arguments?.getString(HOTEL_ID))

            HotelScreen(
                hotelViewModel = DIService.hotelViewModel,
                hotelId = hotelId,
                onBookNowClick = {
                    navController.navigate(Screen.HotelBookingScreen.route + "/$hotelId")
                },
            )
        }
        composable(Screen.Document.route) {
            DocumentScreen(documentViewModel = DIService.documentViewModel)
        }
        composable(Screen.AddDocument.route) {
            CameraScreen(comeback = { navController.navigateUp() })
        }
        composable(Screen.SignUpAddDocument.route) {
            CameraScreen(comeback = { navController.navigateUp() })
        }
        composable(
            route = Screen.HotelBookingScreen.route + "/{$HOTEL_ID}",
            arguments = listOf(navArgument(HOTEL_ID) { type = NavType.StringType }),
        ) { entry ->
            ComposableOrUnauthorizedScreen(navController) {
                val hotelId = UUID.fromString(entry.arguments?.getString(HOTEL_ID))

                HotelBookingScreen(
                    bookingSearchViewModel = DIService.bookingSearchViewModel,
                    hotelId = hotelId,
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