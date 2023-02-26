package com.etu.booking.compose.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etu.booking.default.DefaultModels.HOTEL_MODELS
import com.etu.booking.model.HotelModel
import java.util.*

@Composable
fun HotelScreen(
    hotelId: String,
    onBookNowClick: () -> Unit,
) {
    val hotel = HOTEL_MODELS.first { it.id == UUID.fromString(hotelId) }
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Banner(hotelModel = hotel)
        MainHotelInfo(
            hotelModel = hotel,
            onBookNowClick = onBookNowClick,
        )
        HotelDescription(hotelModel = hotel)
        HotelBookingInfo(hotelModel = hotel)
        HotelFacilities(hotelModel = hotel)
    }
}

@Composable
private fun Banner(hotelModel: HotelModel) {
    Image(
        painter = painterResource(id = hotelModel.imageResource),
        contentDescription = hotelModel.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
    )
}

@Composable
private fun MainHotelInfo(
    hotelModel: HotelModel,
    onBookNowClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 1.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            HotelName(hotelModel = hotelModel)
            HotelBookButton(onBookNowClick = onBookNowClick)
        }
    }
}

@Composable
private fun HotelDescription(hotelModel: HotelModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 1.dp,
    ) {
        Column {
            Text(text = "Description:", style = TextStyle(fontSize = 18.sp, color = Color.Black))
            Text(
                text = hotelModel.description,
                style = TextStyle(fontSize = 18.sp, color = Color.Black)
            )
        }
    }
}

@Composable
private fun HotelBookingInfo(hotelModel: HotelModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 1.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Price per night:",
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )
                Text(text = "${hotelModel.pricePerNight} ${hotelModel.currency}")
            }
            Column {
                Text(text = "Reviews:", style = TextStyle(fontSize = 18.sp, color = Color.Black))
                Row {
                    Icon(
                        imageVector = Icons.TwoTone.Star,
                        contentDescription = Icons.TwoTone.Star.name,
                    )
                    Text(text = hotelModel.score.toString())
                }
            }
        }
    }
}

@Composable
private fun HotelFacilities(hotelModel: HotelModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 1.dp,
    ) {
        Column {
            Text(text = "Facilities:", style = TextStyle(fontSize = 18.sp, color = Color.Black))
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(),
                columns = GridCells.Fixed(count = 3)
            ) {
                items(hotelModel.facilities) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
private fun HotelName(hotelModel: HotelModel) {
    Column {
        Text(text = hotelModel.name, style = MaterialTheme.typography.h4)
        Text(text = hotelModel.address, style = TextStyle(fontSize = 18.sp, color = Color.Black))
    }
}

@Composable
private fun HotelBookButton(
    onBookNowClick: () -> Unit,
) {
    Button(
        onClick = onBookNowClick,
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Text(text = "Book now", color = Color.Black)
    }
}