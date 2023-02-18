package com.etu.booking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.etu.booking.compose.navigation.NavigationBar
import com.etu.booking.compose.screen.SearchScreen
import com.etu.booking.ui.theme.BookingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        SearchScreen()
                        NavigationBar()
                    }
                }
            }
        }
    }
}