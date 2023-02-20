package com.etu.booking.compose.screen

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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
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
import com.etu.booking.default.DefaultModels
import com.etu.booking.default.DefaultModels.BOOKING_STATUSES
import com.etu.booking.model.HistoryHotelModel
import com.etu.booking.view.AuthorizationViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(authorizationViewModel: AuthorizationViewModel) {
    val authorizationState by authorizationViewModel.authorizationState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        HistoryTopBar()
        if (authorizationState.isAuthorized) {
            HistorySortButtons()
            LazyColumn {
                items(DefaultModels.HISTORY_HOTELS_MODELS) { place -> // TODO: change to a repository call
                    HistoryHotelCard(historyHotelModel = place)
                }
            }
        } else {
            UnauthorizedScreen()
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
private fun HistorySortButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatusSort()
        CheckInDateSort()
        CheckOutDateSort()
    }
}

@Composable
private fun CheckInDateSort() {
    var sort by remember { mutableStateOf(0) }

    OutlinedButton(onClick = { sort = (sort + 1) % 3 }) {
        Text(
            text = "Check-in",
            style = MaterialTheme.typography.body2
        )
        SortType(sort = sort)
    }
}

@Composable
private fun CheckOutDateSort() {
    var sort by remember { mutableStateOf(0) }

    OutlinedButton(onClick = { sort = (sort + 1) % 3 }) {
        Text(
            text = "Check-out",
            style = MaterialTheme.typography.body2
        )
        SortType(sort = sort)
    }
}

@Composable
fun SortType(sort: Int) {
    when(sort) {
        1 -> Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = Icons.Filled.KeyboardArrowUp.name,
        )
        2 -> Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = Icons.Filled.KeyboardArrowDown.name,
        )
    }
}

@Composable
private fun StatusSort() {
    var expanded by remember { mutableStateOf(false) }
    var sort by remember { mutableStateOf(BOOKING_STATUSES[0]) }

    OutlinedButton(onClick = { expanded = true }) {
        Text(
            text = sort,
            style = MaterialTheme.typography.body2
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        LazyColumn(
            modifier = Modifier.size(150.dp),
        ) {
            items(BOOKING_STATUSES) {
                DropdownMenuItem(
                    onClick = {
                        sort = it
                        expanded = false
                    }
                ){
                    Text(text = it, fontSize=18.sp)
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
                        getFormattedDate(historyHotelModel.checkOut)
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