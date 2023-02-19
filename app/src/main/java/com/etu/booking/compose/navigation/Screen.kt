package com.etu.booking.compose.navigation

import androidx.annotation.StringRes
import com.etu.booking.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Search : Screen("search", R.string.search_screen)
    object History : Screen("history", R.string.history_screen)
    object Profile : Screen("profile", R.string.profile_screen)
    object More : Screen("more", R.string.more_screen)
}
