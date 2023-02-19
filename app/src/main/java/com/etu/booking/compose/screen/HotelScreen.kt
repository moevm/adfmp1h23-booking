package com.etu.booking.compose.screen

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etu.booking.BookingActivity
import com.etu.booking.default.DefaultModels.HOTEL_MODELS
import com.etu.booking.model.HotelModel

@Composable
fun HotelScreen() {
    val hotel = HOTEL_MODELS[0]
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Banner(hotelModel = hotel)
        MainHotelInfo(hotelModel = hotel)
        HotelDescription(hotelModel = hotel)
        HotelBookingInfo(hotelModel = hotel)
        HotelFacilities(hotelModel = hotel)
    }
}

@Composable
fun Banner(hotelModel: HotelModel) {
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
fun MainHotelInfo(hotelModel: HotelModel) {
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
            Button()
        }
    }
}

@Composable
fun HotelDescription(hotelModel: HotelModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 1.dp,
    ) {
        Column {
            Text(text = "Description:", style = TextStyle(fontSize = 18.sp, color = Color.Black))
            Text(text = hotelModel.description, style = TextStyle(fontSize = 18.sp, color = Color.Black))
        }
    }
}

@Composable
fun HotelBookingInfo(hotelModel: HotelModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = 1.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Price per night:", style = TextStyle(fontSize = 18.sp, color = Color.Black))
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
fun HotelFacilities(hotelModel: HotelModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
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
fun HotelName(hotelModel: HotelModel) {
    Column {
        Text(text = hotelModel.name, style = TextStyle(fontSize = 18.sp, color = Color.Black))
        Text(text = hotelModel.address, style = TextStyle(fontSize = 18.sp, color = Color.Black))
    }
}

@Composable
fun Button() {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(context, BookingActivity::class.java)
            context.startActivity(intent)
        },
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Text(text = "Book now", color = Color.Black)
    }
}