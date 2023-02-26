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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.R
import com.etu.booking.compose.component.NothingToDisplay
import com.etu.booking.compose.component.ProgressIndicator
import com.etu.booking.compose.component.SortingButton
import com.etu.booking.model.BookingSearchModel
import com.etu.booking.model.HotelCardModel
import com.etu.booking.model.filter.BookingSearchFilter
import com.etu.booking.viewmodel.BookingSearchViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun BookingListScreen(
    bookingSearchViewModel: BookingSearchViewModel,
    onCardClick: (UUID) -> Unit,
) {
    val isLoading by bookingSearchViewModel.isLoading.collectAsState()
    val hotels by bookingSearchViewModel.hotels.collectAsState()
    val bookingSearchModel by bookingSearchViewModel.booking.collectAsState()
    val filter by bookingSearchViewModel.filter.collectAsState()

    LaunchedEffect(Unit) {
        bookingSearchViewModel.updateHotels()
    }

    BookingListScreen(
        bookingSearchModel = bookingSearchModel,
        isLoading = isLoading,
        hotels = hotels,
        filter = filter,
        onCardClick = onCardClick,
        onPriceSortingClick = { bookingSearchViewModel.nextPriceSorting() },
        onRatingSortingClick = { bookingSearchViewModel.nextRatingSorting() },
        onDistanceSortingClick = { bookingSearchViewModel.nextDistanceSorting() },
    )
}

@Composable
private fun BookingListScreen(
    bookingSearchModel: BookingSearchModel,
    isLoading: Boolean,
    hotels: List<HotelCardModel>,
    filter: BookingSearchFilter,
    onCardClick: (UUID) -> Unit,
    onPriceSortingClick: () -> Unit,
    onRatingSortingClick: () -> Unit,
    onDistanceSortingClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        BookingSearchTopBar(bookingSearchModel)
        SearchSortButtons(
            filter = filter,
            onPriceSortingClick = onPriceSortingClick,
            onRatingSortingClick = onRatingSortingClick,
            onDistanceSortingClick = onDistanceSortingClick,
        )
        ProgressIndicator(enable = isLoading) {
            NothingToDisplay(enable = hotels.isEmpty()) {
                LazyColumn {
                    items(hotels) { place ->
                        HotelCard(
                            hotelCardModel = place,
                            onClick = onCardClick,
                        )
                    }
                }
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
private fun SearchSortButtons(
    filter: BookingSearchFilter,
    onPriceSortingClick: () -> Unit,
    onRatingSortingClick: () -> Unit,
    onDistanceSortingClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SortingButton(
            text = stringResource(id = R.string.sort_price),
            sortingType = filter.price,
            onClick = onPriceSortingClick
        )
        SortingButton(
            text = stringResource(id = R.string.sort_rating),
            sortingType = filter.rating,
            onClick = onRatingSortingClick
        )
        SortingButton(
            text = stringResource(id = R.string.sort_distance),
            sortingType = filter.distance,
            onClick = onDistanceSortingClick
        )
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun HotelCard(
    hotelCardModel: HotelCardModel,
    onClick: (UUID) -> Unit,
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
        onClick = { onClick(hotelCardModel.id) },
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
