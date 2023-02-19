package com.etu.booking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.etu.booking.compose.screen.BookingListScreen
import com.etu.booking.compose.screen.HotelScreen
import com.etu.booking.default.DefaultModels
import com.etu.booking.ui.theme.BookingTheme

class SearchScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
//                    HotelScreen()
                    BookingListScreen(DefaultModels.BOOKING_SEARCH_MODEL) // TODO: change to a repository call
                }
            }
        }
    }
}