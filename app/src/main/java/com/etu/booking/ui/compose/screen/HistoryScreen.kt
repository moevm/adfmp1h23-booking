package com.etu.booking.ui.compose.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etu.booking.R
import com.etu.booking.model.BookingStatus
import com.etu.booking.model.HistoryHotelModel
import com.etu.booking.model.filter.HistoryFilter
import com.etu.booking.ui.compose.component.NothingToDisplay
import com.etu.booking.ui.compose.component.ProgressIndicator
import com.etu.booking.ui.compose.component.SortingButton
import com.etu.booking.viewmodel.HistoryViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel) {
    val isLoading by historyViewModel.isLoading.collectAsState()
    val hotels by historyViewModel.hotels.collectAsState()
    val filter by historyViewModel.filter.collectAsState()

    LaunchedEffect(Unit) {
        historyViewModel.updateHotels()
    }

    HistoryScreen(
        isLoading = isLoading,
        filter = filter,
        hotels = hotels,
        onCheckInSortingClick = { historyViewModel.nextCheckInSorting() },
        onCheckOutSortingClick = { historyViewModel.nextCheckOutSorting() },
        onDropdownBookingStatusSortingClick = { historyViewModel.updateBookingStatusSorting(it) }
    )
}

@Composable
private fun HistoryScreen(
    isLoading: Boolean,
    filter: HistoryFilter,
    hotels: List<HistoryHotelModel>,
    onCheckInSortingClick: () -> Unit,
    onCheckOutSortingClick: () -> Unit,
    onDropdownBookingStatusSortingClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        HistoryTopBar()
        HistorySortButtons(
            filter = filter,
            onCheckInSortingClick = onCheckInSortingClick,
            onCheckOutSortingClick = onCheckOutSortingClick,
            onDropdownBookingStatusSortingClick = onDropdownBookingStatusSortingClick,
        )
        ProgressIndicator(enable = isLoading) {
            NothingToDisplay(enable = hotels.isEmpty()) {
                HistoryCardList(
                    hotels = hotels,
                )
            }
        }
    }
}

@Composable
fun HistoryCardList(hotels: List<HistoryHotelModel>) {
    LazyColumn {
        items(hotels) { place -> // TODO: change to a repository call
            HistoryHotelCard(historyHotelModel = place)
        }
    }
}

@Composable
private fun HistoryTopBar() {
    TopAppBar {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(id = R.string.history_screen_title),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
private fun HistorySortButtons(
    filter: HistoryFilter,
    onCheckInSortingClick: () -> Unit,
    onCheckOutSortingClick: () -> Unit,
    onDropdownBookingStatusSortingClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatusSort(
            sortingStatus = filter.bookingStatus,
            onDropdownBookingStatusSortingClick = onDropdownBookingStatusSortingClick,
        )
        SortingButton(
            text = stringResource(id = R.string.sort_check_in),
            sortingType = filter.checkIn,
            onClick = onCheckInSortingClick,
        )
        SortingButton(
            text = stringResource(id = R.string.sort_check_out),
            sortingType = filter.checkOut,
            onClick = onCheckOutSortingClick
        )
    }
}

@Composable
private fun StatusSort(
    sortingStatus: BookingStatus?,
    onDropdownBookingStatusSortingClick: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedButton(onClick = { expanded = true }) {
        Text(
            text = sortingStatus?.name ?: DEFAULT_BOOKING_STATUS,
            style = MaterialTheme.typography.body2
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        LazyColumn(
            modifier = Modifier.size(190.dp),
        ) {
            items(BOOKING_STATUSES) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onDropdownBookingStatusSortingClick(it)
                    }
                ) {
                    Text(text = it, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
private fun HistoryHotelCard(historyHotelModel: HistoryHotelModel) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HotelCardDescription(historyHotelModel)
        }
    }
}

@Composable
private fun HotelCardDescription(historyHotelModel: HistoryHotelModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(historyHotelModel.name, style = MaterialTheme.typography.h4)
        Column {
            Text(
                stringResource(id = R.string.check_in_date) +
                        stringResource(id = R.string.colon_with_space_after) +
                        getFormattedDate(historyHotelModel.checkIn)
            )
            Text(
                stringResource(id = R.string.check_out_date) +
                        stringResource(id = R.string.colon_with_space_after) +
                        getFormattedDate(historyHotelModel.checkOut)
            )
            Text(
                stringResource(id = R.string.address) +
                        stringResource(id = R.string.colon_with_space_after) +
                        historyHotelModel.address
            )
            Text(
                stringResource(id = R.string.full_price) +
                        stringResource(id = R.string.colon_with_space_after) +
                        historyHotelModel.fullPrice +
                        stringResource(id = R.string.space) +
                        historyHotelModel.currency
            )
        }
    }
}

@Composable
private fun getFormattedDate(date: LocalDate): String {
    return stringResource(id = R.string.history_full_date_pattern)
        .let { DateTimeFormatter.ofPattern(it) }
        .let { date.format(it) }
}

private const val DEFAULT_BOOKING_STATUS = "ALL"
private val BOOKING_STATUSES = listOf(DEFAULT_BOOKING_STATUS) +
        BookingStatus.values().map { it.name }