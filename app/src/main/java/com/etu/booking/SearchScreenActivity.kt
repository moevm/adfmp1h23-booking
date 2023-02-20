package com.etu.booking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.etu.booking.compose.screen.BookingListScreen
import com.etu.booking.compose.screen.SomethingWentWrongScreen
import com.etu.booking.control.readFromIntent
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.ui.theme.BookingTheme

class SearchScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val searchModel = try {
                        readFromIntent("search", intent) as BookingSearchModel
                    } catch (ex: Exception) {
                        null
                    }
                    searchModel?.let { BookingListScreen(searchModel) }
                        ?: SomethingWentWrongScreen() // TODO go out and come in okay
                }
            }
        }
    }
}