package com.etu.booking.compose.screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.AboutUsActivity
import com.etu.booking.R

@Composable
fun MoreScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        MoreTopBar()
        LazyColumn {
            item { AboutUsCard() }
        }
    }
}

@Composable
private fun MoreTopBar() {
    TopAppBar {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(id = R.string.more_screen_title),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun AboutUsCard() {
    val context = LocalContext.current
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
        onClick = { context.startActivity(Intent(context, AboutUsActivity::class.java)) }
    ) {
        Text(
            text = stringResource(id = R.string.about_us_button),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
