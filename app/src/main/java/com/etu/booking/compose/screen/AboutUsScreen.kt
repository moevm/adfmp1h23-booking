package com.etu.booking.compose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.R

@Composable
fun AboutUsScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        AboutUsTopBar()
        AboutUsCard()
    }
}

@Composable
private fun AboutUsTopBar() {
    TopAppBar {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(id = R.string.about_us_screen_title),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
private fun AboutUsCard() {
    Card(
        modifier = Modifier.padding(8.dp).fillMaxHeight(),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(id = R.string.about_us_description),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
