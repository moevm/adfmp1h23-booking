package com.etu.booking.compose.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.AboutUsActivity
import com.etu.booking.R
import com.etu.booking.view.AuthorizationViewModel

@Composable
fun MoreScreen(authorizationViewModel: AuthorizationViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        MoreTopBar()
        LazyColumn {
            item { AuthorizationCard(authorizationViewModel) }
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

@Composable
private fun AuthorizationCard(authorizationViewModel: AuthorizationViewModel) {
    val authorizationState by authorizationViewModel.authorizationState.collectAsState()

    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = R.string.authorization_toggle),
            )
            Switch(
                checked = authorizationState.isAuthorized,
                onCheckedChange = {
                    if (it) {
                        authorizationViewModel.logIn()
                    } else {
                        authorizationViewModel.logOut()
                    }
                }
            )
        }

    }
}
