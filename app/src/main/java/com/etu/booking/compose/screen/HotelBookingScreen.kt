package com.etu.booking.compose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.R
import com.etu.booking.compose.component.FailedAction
import com.etu.booking.compose.component.ProgressIndicator
import com.etu.booking.compose.component.SuccessAction
import com.etu.booking.view.BookingSearchViewModel

@Composable
fun HotelBookingScreen(
    bookingSearchViewModel: BookingSearchViewModel,
    hotelId: String,
    onSuccessClick: () -> Unit,
    onFailedClick: () -> Unit,
) {
    val isLoading by bookingSearchViewModel.isLoading.collectAsState()
    val isSuccessfullyBooked by bookingSearchViewModel.isSuccessfullyBooked.collectAsState()

    LaunchedEffect(Unit) {
        bookingSearchViewModel.book(hotelId)
    }

    when (isLoading) {
        true -> ProgressIndicator()
        false -> {
            HotelBookingTopBar()
            BookingResult(
                isSuccessfullyBooked = isSuccessfullyBooked,
                onSuccessClick = onSuccessClick,
                onFailedClick = onFailedClick,
            )
        }

    }
}

@Composable
private fun HotelBookingTopBar() {
    TopAppBar {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(id = R.string.hotel_booking_title),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
private fun BookingResult(
    isSuccessfullyBooked: Boolean,
    onSuccessClick: () -> Unit,
    onFailedClick: () -> Unit,
) {
    when (isSuccessfullyBooked) {
        true -> SuccessAction(
            text = stringResource(id = R.string.successfully_booked),
            onClick = onSuccessClick
        )
        false -> FailedAction(
            text = stringResource(id = R.string.failed_action),
            onClick = onFailedClick
        )
    }
}
