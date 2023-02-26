package com.etu.booking.compose.screen

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.etu.booking.R
import com.etu.booking.compose.component.Input
import com.etu.booking.compose.component.PushButton
import com.etu.booking.default.DefaultModels
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.LocationModel
import com.etu.booking.viewmodel.BookingSearchViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun SearchScreen(
    viewModel: BookingSearchViewModel,
    onSearch: () -> Unit,
) {

    val bookingState by viewModel.booking.collectAsState()

    val onClick = {
        if (isBookEnable(bookingState)) {
            onSearch()
        } else {
            viewModel.highlightInputs()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(top = 50.dp),
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 50.sp,
            text = "Booking"
        )
        Text(
            text = "Book your hotel",
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
        LocationInput(
            bookingSearchModel = bookingState,
            onChange = { viewModel.setLocation(it) },
        )
        DateSearch(viewModel = viewModel, bookingSearchModel = bookingState)
        PriceSearch(viewModel = viewModel, bookingSearchModel = bookingState)
        MaxDistance(
            bookingSearchModel = bookingState,
            onChange = { viewModel.setMaxDistanceToCenterInKm(it) }
        )
        GuestInput(
            bookingSearchModel = bookingState,
            onChange = { viewModel.setGuestAmount(it) }
        )
        PushButton(
            modifier = Modifier.padding(bottom = 30.dp),
            text = "Book",
            onClick = onClick
        )
    }
}

@Composable
private fun LocationInput(
    bookingSearchModel: BookingSearchModel,
    onChange: (LocationModel) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column {
        Input(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 2.dp)
                .fillMaxWidth(),
            text = LocationModel.print(bookingSearchModel.location),
            placeholder = "Location",
            onChange = {
                onChange(LocationModel.create(it))
                expanded = true
            },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            keyBoardActions = KeyboardActions(
                onAny = {
                    focusManager.clearFocus()
                }
            ),
            isError = bookingSearchModel.errorModel.location,
            errorMessage = stringResource(id = R.string.location_error_message)
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = false),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(shape = RoundedCornerShape(20.dp), color = Color.White)
            ) {
                for (city in DefaultModels.CITIES) { // TODO: change to a repository call
                    Text(
                        modifier = Modifier
                            .clickable {
                                onChange(city)
                                expanded = false
                            }
                            .fillMaxWidth()
                            .padding(10.dp),
                        text = "${city.country}, ${city.city}"
                    )
                }
            }
        }
    }
}

@Composable
private fun DateSearch(
    bookingSearchModel: BookingSearchModel,
    viewModel: BookingSearchViewModel,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        CheckInDate(
            bookingSearchModel = bookingSearchModel,
            onChange = { viewModel.setCheckIn(it) },
        )
        CheckOutDate(
            bookingSearchModel = bookingSearchModel,
            onChange = { viewModel.setCheckOut(it) },
        )
    }
}

@Composable
private fun CheckInDate(
    bookingSearchModel: BookingSearchModel,
    onChange: (LocalDate) -> Unit,
) {
    val context = LocalContext.current
    Input(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(start = 8.dp, end = 1.dp, bottom = 2.dp, top = 2.dp)
            .clickable {
                showDatePickerDialog(context, bookingSearchModel.checkIn) { date -> onChange(date) }
            },
        text = if (bookingSearchModel.checkIn != null) dateFormat.format(bookingSearchModel.checkIn) else "",
        placeholder = "Check-in",
        onChange = {
            onChange(LocalDate.parse(it, dateFormat))
        },
        imeAction = ImeAction.Next,
        isEnabled = false,
        isError = bookingSearchModel.errorModel.checkIn,
        errorMessage = stringResource(id = R.string.not_past_date_error_message)
    )
}

@Composable
private fun CheckOutDate(
    bookingSearchModel: BookingSearchModel,
    onChange: (LocalDate) -> Unit,
) {
    val context = LocalContext.current
    Input(
        modifier = Modifier
            .padding(start = 1.dp, end = 8.dp, bottom = 2.dp, top = 2.dp)
            .clickable {
                showDatePickerDialog(
                    context,
                    bookingSearchModel.checkOut
                ) { date -> onChange(date) }
            },
        text = if (bookingSearchModel.checkOut != null) dateFormat.format(bookingSearchModel.checkOut) else "",
        placeholder = "Check-out",
        onChange = {
            onChange(LocalDate.parse(it, dateFormat))
        },
        imeAction = ImeAction.Next,
        isEnabled = false,
        isError = bookingSearchModel.errorModel.checkOut,
        errorMessage = stringResource(id = R.string.not_past_date_error_message)
    )
}

@Composable
private fun PriceSearch(
    bookingSearchModel: BookingSearchModel,
    viewModel: BookingSearchViewModel,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        MinPrice(
            bookingSearchModel = bookingSearchModel,
            onChange = { viewModel.setMinPriceRepNight(it) },
        )
        MaxPrice(
            bookingSearchModel = bookingSearchModel,
            onChange = { viewModel.setMaxPriceRepNight(it) },
        )
    }
}

@Composable
private fun MinPrice(
    bookingSearchModel: BookingSearchModel,
    onChange: (Int?) -> Unit,
) {

    Input(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(start = 8.dp, end = 1.dp, bottom = 2.dp, top = 2.dp),
        text = if (bookingSearchModel.minPricePerNight != null) bookingSearchModel.minPricePerNight.toString() else "",
        placeholder = "Min price per night",
        onChange = {
            if (it.isEmpty()) {
                onChange(null)
            } else if (it.last().isDigit()) {
                onChange(it.toInt())
            }
        },
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Decimal,
        isError = bookingSearchModel.errorModel.minPricePerNight,
        errorMessage = stringResource(id = R.string.positive_error_message)
    )
}

@Composable
private fun MaxPrice(
    bookingSearchModel: BookingSearchModel,
    onChange: (Int?) -> Unit,
) {

    Input(
        modifier = Modifier
            .padding(start = 1.dp, end = 8.dp, bottom = 2.dp, top = 2.dp),
        text = if (bookingSearchModel.maxPricePerNight != null) bookingSearchModel.maxPricePerNight.toString() else "",
        placeholder = "Max price per night",
        onChange = {
            if (it.isEmpty()) {
                onChange(null)
            } else if (it.last().isDigit()) {
                onChange(it.toInt())
            }
        },
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Decimal,
        isError = bookingSearchModel.errorModel.maxPricePerNight,
        errorMessage = stringResource(id = R.string.positive_error_message)
    )
}

@Composable
private fun MaxDistance(
    bookingSearchModel: BookingSearchModel,
    onChange: (Int?) -> Unit,
) {
    Input(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        text = if (bookingSearchModel.maxDistanceToCenterInKm != null)
            bookingSearchModel.maxDistanceToCenterInKm.toString()
        else
            "",
        placeholder = "Max destination from center",
        onChange = {
            if (it.isEmpty()) {
                onChange(null)
            } else if (it.last().isDigit()) {
                onChange(it.toInt())
            }
        },
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Decimal,
        isError = bookingSearchModel.errorModel.maxDistanceToCenterInKm,
        errorMessage = stringResource(id = R.string.non_negative_error_message)
    )
}

@Composable
private fun GuestInput(
    bookingSearchModel: BookingSearchModel,
    onChange: (Int?) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Input(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .fillMaxWidth(),
        text = if (bookingSearchModel.guestsAmount != null) bookingSearchModel.guestsAmount.toString() else "",
        placeholder = "Guests",
        onChange = {
            if (it.isEmpty()) {
                onChange(null)
            } else if (it.last().isDigit()) {
                onChange(it.toInt())
            }
        },
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Decimal,
        keyBoardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        isError = bookingSearchModel.errorModel.guestsAmount,
        errorMessage = stringResource(id = R.string.guests_amount_error_message)
    )
}

private var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
fun showDatePickerDialog(
    context: Context,
    date: LocalDate?,
    onChange: (LocalDate) -> Unit,
) {
    val calendar = getCalendar(date)
    DatePickerDialog(
        context, { _, year, month, day ->
            onChange(getPickedDateAsString(year, month, day))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
        .show()
}

private fun getCalendar(date: LocalDate?) = if (date == null) {
    Calendar.getInstance()
} else {
    getLastPickedDateCalendar(date)
}


private fun getLastPickedDateCalendar(date: LocalDate): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(date.year, date.month.value - 1, date.dayOfMonth)
    return calendar
}

private fun getPickedDateAsString(year: Int, month: Int, day: Int): LocalDate {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    return LocalDate.of(year, month + 1, day)
}

private fun isBookEnable(bookingSearchModel: BookingSearchModel): Boolean = bookingSearchModel.run {
    location != null
            && checkIn != null
            && checkOut != null
            && guestsAmount != null
            && !errorModel.location
            && !errorModel.checkIn
            && !errorModel.checkOut
            && !errorModel.minPricePerNight
            && !errorModel.maxPricePerNight
            && !errorModel.maxDistanceToCenterInKm
            && !errorModel.guestsAmount
}
