package com.example.booking.compose

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.booking.SearchScreenActivity
import com.example.booking.model.CityModel
import com.example.booking.view.BookingSearchViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BookingSearch(viewModel: BookingSearchViewModel = viewModel()){
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(0.5f)
            .shadow(8.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Book your hotel",
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            LocationInput(viewModel = viewModel)
            CheckInDate(viewModel)
            CheckOutDate(viewModel)
            IntInput(viewModel)
            Button(viewModel)
        }
    }
}

@Composable
fun LocationInput(
    modifier: Modifier = Modifier,
    viewModel: BookingSearchViewModel,
) {
    val focusManager = LocalFocusManager.current
    var showDialog by remember { mutableStateOf(false) }

    Column {
        Input(
            modifier = modifier,
            text = viewModel.location?.let { "${it.country} ${it.name}" } ?: "",
            placeholder = "Location",
            onChange = {
                showDialog = true
                viewModel.location = CityModel.create(it)
            },
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text,
            keyBoardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
        LocationPickerDialog(
            onSelection = { location ->
                viewModel.location = location
            }
        )
    }

}

@Composable
fun Input(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyBoardActions,
        enabled = isEnabled,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black
        ),
        placeholder = {
            Text(text = placeholder, style = TextStyle(fontSize = 18.sp, color = Color.LightGray))
        }
    )
}

@Composable
fun LocationPickerDialog(
    onSelection: (CityModel) -> Unit,
) {
    var expanded by remember { mutableStateOf(true) }
    var cities: List<CityModel> by remember { mutableStateOf(listOf(CityModel("Vitebsk","Belarus"), CityModel("Minsk", "Belarus")))}

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 40.dp)
                .background(shape = RoundedCornerShape(20.dp), color = Color.White)
        ) {
            for (city in cities) {
                Text(
                    modifier = Modifier
                        .clickable {
                            onSelection(city)
                        }
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "${city.name} ${city.country}"
                )
            }
        }
    }
}

@Composable
fun IntInput(
    viewModel: BookingSearchViewModel,
){
    val focusManager = LocalFocusManager.current

    Input(
        text = if (viewModel.guestNumber != null) viewModel.guestNumber.toString() else "",
        placeholder = "Guests",
        onChange = {
            viewModel.guestNumber = it.toInt()
        },
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Decimal,
        keyBoardActions = KeyboardActions(
            onNext = {
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
fun CheckInDate(
    viewModel: BookingSearchViewModel,
) {
    val context = LocalContext.current
    Input(
        modifier = Modifier.clickable {
            showDatePickerDialog(context, viewModel.checkIn){date -> viewModel.checkIn = date }
        },
        text = if (viewModel.checkIn != null) dateFormat.format(viewModel.checkIn) else "",
        placeholder = "CheckIn",
        onChange = {
            viewModel.checkIn = LocalDate.parse(it, dateFormat)
        },
        isEnabled = false
    )
}

@Composable
fun CheckOutDate(
    viewModel: BookingSearchViewModel,
) {
    val context = LocalContext.current
    Input(
        modifier = Modifier.clickable {
            showDatePickerDialog(context, viewModel.checkOut){date -> viewModel.checkOut = date }
        },
        text = if (viewModel.checkOut != null) dateFormat.format(viewModel.checkOut) else "",
        placeholder = "CheckIn",
        onChange = {
            viewModel.checkOut = LocalDate.parse(it, dateFormat)
        },
        isEnabled = false
    )
}

@Composable
fun Button(
    viewModel: BookingSearchViewModel,
) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(context, SearchScreenActivity::class.java)
            intent.putExtra("booking_location", viewModel.location!!.name)
            context.startActivity(intent)
        },
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Text(text = "Book", color = Color.Black)
    }
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
    calendar.set(date.year, date.month.value, date.dayOfMonth)
    return calendar
}

private fun getPickedDateAsString(year: Int, month: Int, day: Int): LocalDate {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    return LocalDate.of(year, month,day)
}