package com.etu.booking.ui.compose.navigation

import DocumentScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.etu.booking.dependecyinjection.holder.ViewModelHolder
import com.etu.booking.dependecyinjection.provider.CredentialProvider
import com.etu.booking.dependecyinjection.provider.ViewModelProvider
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
import com.etu.booking.utils.authorized
import java.util.*

@Composable
fun NavigationController(
    innerPadding: PaddingValues,
    navController: NavHostController,
) {
    val viewModelHolder = getViewModelHolder()
        .also { CredentialProvider.setCredentialState(it.credentialViewModel.credentialState) }

    NavHost(
        navController = navController,
        startDestination = Screen.Search.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Search.route) {
            SearchScreen(
                viewModel = viewModelHolder.bookingSearchViewModel,
                onSearch = { navController.navigate(Screen.BookingList.route) }
            )
        }
        composable(Screen.History.route) {
            ComposableOrUnauthorizedScreen(navController, viewModelHolder) {
                HistoryScreen(historyViewModel = viewModelHolder.historyViewModel)
            }
        }
        composable(Screen.Profile.route) {
            ComposableOrUnauthorizedScreen(navController, viewModelHolder) {
                ProfileScreen(
                    profileViewModel = viewModelHolder.profileViewModel,
                    onAddDocumentClick = { navController.navigate(Screen.AddDocument.route) },
                    onShowDocumentsClick = { navController.navigate(Screen.Document.route) },
                )
            }
        }
        composable(Screen.More.route) {
            MoreScreen(
                credentialViewModel = viewModelHolder.credentialViewModel,
                onAboutUsCardClick = { navController.navigate(Screen.AboutUs.route) }
            )
        }
        composable(Screen.AboutUs.route) { AboutUsScreen() }
        composable(Screen.Unauthorized.route) {
            UnauthorizedScreenWrapper(navController)
        }
        composable(Screen.BookingList.route) {
            BookingListScreen(
                bookingSearchViewModel = viewModelHolder.bookingSearchViewModel,
                onCardClick = { hotelId ->
                    navController.navigate(Screen.Hotel.route + "/$hotelId")
                }
            )
        }
        composable(Screen.Auth.route) {
            AuthScreen(
                authViewModel = viewModelHolder.authViewModel,
                profileViewModel = viewModelHolder.profileViewModel,
                credentialViewModel = viewModelHolder.credentialViewModel,
                onAuthorized = { navController.navigateUp() },
                onAddDocumentClick = { navController.navigate(Screen.SignUpAddDocument.route) },
            )
        }
        composable(
            route = Screen.Hotel.route + "/{$HOTEL_ID}",
            arguments = listOf(navArgument(HOTEL_ID) { type = NavType.StringType }),
        ) { entry ->
            val hotelId = UUID.fromString(entry.arguments?.getString(HOTEL_ID))

            HotelScreen(
                hotelViewModel = viewModelHolder.hotelViewModel,
                hotelId = hotelId,
                onBookNowClick = {
                    navController.navigate(Screen.HotelBookingScreen.route + "/$hotelId")
                },
            )
        }
        composable(Screen.Document.route) {
            DocumentScreen(documentViewModel = viewModelHolder.documentViewModel)
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
            ComposableOrUnauthorizedScreen(navController, viewModelHolder) {
                val hotelId = UUID.fromString(entry.arguments?.getString(HOTEL_ID))

                HotelBookingScreen(
                    bookingSearchViewModel = viewModelHolder.bookingSearchViewModel,
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
    viewModelHolder: ViewModelHolder,
    content: @Composable () -> Unit,
) {
    val credentialState by viewModelHolder.credentialViewModel
        .credentialState
        .collectAsState()

    if (credentialState.authorized()) {
        content()
    } else {
        UnauthorizedScreenWrapper(navController)
    }
}

@Composable
private fun UnauthorizedScreenWrapper(navController: NavHostController) {
    UnauthorizedScreen(
        onStartSignIn = { navController.navigate(Screen.Auth.route) }
    )
}

@Composable
private fun getViewModelHolder(): ViewModelHolder {
    return ViewModelHolder(
        credentialViewModel = getViewModel(),
        authViewModel = getViewModel(),
        bookingSearchViewModel = getViewModel(),
        documentViewModel = getViewModel(),
        historyViewModel = getViewModel(),
        hotelViewModel = getViewModel(),
        profileViewModel = getViewModel(),
    )
}

@Composable
private inline fun <reified T : ViewModel> getViewModel(): T {
    return viewModel(factory = ViewModelProvider.Factory)
}

private const val HOTEL_ID = "hotelId"