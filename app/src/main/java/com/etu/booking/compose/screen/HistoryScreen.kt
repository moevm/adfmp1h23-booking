package com.etu.booking.compose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.R
import com.etu.booking.default.DefaultModels
import com.etu.booking.model.HistoryHotelModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        HistoryTopBar()
        LazyColumn {
            items(DefaultModels.HISTORY_HOTELS_MODELS) { place -> // TODO: change to a repository call
                HistoryHotelCard(historyHotelModel = place)
            }
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