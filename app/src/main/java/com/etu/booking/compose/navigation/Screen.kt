package com.etu.booking.compose.navigation

import androidx.annotation.StringRes
import com.etu.booking.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Search : Screen("search", R.string.search_screen)
    object History : Screen("history", R.string.history_screen)
    object Profile : Screen("profile", R.string.profile_screen)
    object More : Screen("more", R.string.more_screen)
    object AboutUs : Screen("about-us", R.string.about_us_screen)
    object Unauthorized : Screen("unauthorized", R.string.unauthorized_screen)
    object BookingList : Screen("booking-list", R.string.booking_list_screen)
    object Auth : Screen("auth", R.string.auth_screen)
    object Document : Screen("document-list", R.string.document_screen)
    object AddDocument : Screen("add-document", R.string.document_screen)
    object Hotel : Screen("hotel", R.string.hotel_screen)
    object HotelBookingScreen : Screen("hotel-booking", R.string.hotel_booking_screen)
}
