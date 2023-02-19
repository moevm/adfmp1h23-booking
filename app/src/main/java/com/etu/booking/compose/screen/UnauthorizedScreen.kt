package com.etu.booking.compose.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.AuthorizationActivity
import com.etu.booking.R

@Composable
fun UnauthorizedScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val context = LocalContext.current
        Text(
            text = stringResource(id = R.string.unauthorized_description),
            style = MaterialTheme.typography.body1
        )
        OutlinedButton(
            onClick = { context.startActivity(Intent(context, AuthorizationActivity::class.java)) }
        ) {
            Text(
                text = stringResource(id = R.string.authorization_button),
                style = MaterialTheme.typography.body1
            )
        }
    }
}