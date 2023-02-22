package com.etu.booking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.etu.booking.compose.dependecyinjection.DIService
import com.etu.booking.compose.navigation.NavigationBar
import com.etu.booking.ui.theme.BookingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    DIService.SearchScreen()
                    NavigationBar()
                }
            }
        }
    }
}