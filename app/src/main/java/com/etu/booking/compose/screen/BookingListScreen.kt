package com.etu.booking.compose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.R
import com.etu.booking.compose.component.ProgressIndicator
import com.etu.booking.control.sort
import com.etu.booking.default.DefaultModels
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.HotelCardModel
import com.etu.booking.view.BookingSearchViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BookingListScreen(
    bookingSearchViewModel: BookingSearchViewModel,
    onCardClick: (String) -> Unit,
) {
    val isLoading by bookingSearchViewModel.isLoading.collectAsState()
    val list = remember { mutableStateOf(DefaultModels.HOTEL_CARDS_MODELS) } // TODO: change to a repository call

    when {
        isLoading -> ProgressIndicator()
        else -> BookingList(
            bookingSearchViewModel = bookingSearchViewModel,
            list = list,
            onCardClick = onCardClick,
        )
    }
}

@Composable
fun BookingList(
    bookingSearchViewModel: BookingSearchViewModel,
    list: MutableState<List<HotelCardModel>>,
    onCardClick: (String) -> Unit,
) {
    val bookingSearchModel = bookingSearchViewModel.booking.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        BookingSearchTopBar(bookingSearchModel.value)
        SearchSortButtons(list = list)
        LazyColumn {
            items(list.value) { place ->
                HotelCard(
                    hotelCardModel = place,
                    onClick = onCardClick,
                )
            }
        }
    }
}

@Composable
private fun BookingSearchTopBar(bookingSearchModel: BookingSearchModel) {
    TopAppBar {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            val unknown = stringResource(id = R.string.unknown)
            val comma = stringResource(id = R.string.comma_with_space_after)
            Text(
                text = (bookingSearchModel.location?.city ?: unknown) +
                        comma +
                        (bookingSearchModel.location?.country ?: unknown),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = getFormattedDateOrDefault(bookingSearchModel.checkIn, unknown) +
                        stringResource(id = R.string.dash_with_spaces_around) +
                        getFormattedDateOrDefault(bookingSearchModel.checkOut, unknown) +
                        comma +
                        (bookingSearchModel.guestsAmount ?: unknown) +
                        stringResource(id = R.string.guests)

            )
        }
    }
}

@Composable
private fun getFormattedDateOrDefault(date: LocalDate?, defaultValue: String): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM")
    return date?.format(dateTimeFormatter) ?: defaultValue
}

@Composable
private fun SearchSortButtons(list: MutableState<List<HotelCardModel>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        PriceSort(list = list)
        RatingSort(list = list)
        DestinationSort(list = list)
    }
}

@Composable
private fun RatingSort(list: MutableState<List<HotelCardModel>>) {
    var sort by remember { mutableStateOf(0) }

    OutlinedButton(onClick = {
        sort = (sort + 1) % 3
        list.value = sort(sort, list.value) { it.score }
    }
    ) {
        Text(
            text = "Rating",
            style = MaterialTheme.typography.body2
        )
        SortType(sort = sort)
    }
}

@Composable
private fun PriceSort(list: MutableState<List<HotelCardModel>>) {
    var sort by remember { mutableStateOf(0) }

    OutlinedButton(
        onClick = {
            sort = (sort + 1) % 3
            list.value = sort(sort, list.value) { it.pricePerNight }
        }
    ) {
        Text(
            text = "Price",
            style = MaterialTheme.typography.body2
        )
        SortType(sort = sort)
    }
}

@Composable
private fun DestinationSort(list: MutableState<List<HotelCardModel>>) {
    var sort by remember { mutableStateOf(0) }

    OutlinedButton(
        onClick = {
            sort = (sort + 1) % 3
            list.value = sort(sort, list.value) { it.kmFromCenter }
        }
    ) {
        Text(
            text = "Distance",
            style = MaterialTheme.typography.body2
        )
        SortType(sort = sort)
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun HotelCard(
    hotelCardModel: HotelCardModel,
    onClick: (String) -> Unit,
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
        onClick = { onClick(hotelCardModel.id.toString()) },
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HotelCardImage(hotelCardModel)
            HotelCardDescription(hotelCardModel)
        }
    }
}

@Composable
private fun HotelCardImage(hotelCardModel: HotelCardModel) {
    Image(
        painter = painterResource(id = hotelCardModel.imageResource),
        contentDescription = hotelCardModel.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(144.dp)
    )
}

@Composable
private fun HotelCardDescription(hotelCardModel: HotelCardModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(hotelCardModel.name, style = MaterialTheme.typography.h4)
        Text(
            stringResource(id = R.string.from_center) +
                    stringResource(id = R.string.colon_with_space_after) +
                    hotelCardModel.kmFromCenter +
                    stringResource(id = R.string.space) +
                    stringResource(id = R.string.km)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(id = R.string.price_per_night) +
                        stringResource(id = R.string.colon_with_space_after) +
                        hotelCardModel.pricePerNight +
                        stringResource(id = R.string.space) +
                        hotelCardModel.currency
            )
            Row {
                Icon(
                    imageVector = Icons.TwoTone.Star,
                    contentDescription = Icons.TwoTone.Star.name,
                )
                Text(text = hotelCardModel.score.toString())
            }
        }
    }
}