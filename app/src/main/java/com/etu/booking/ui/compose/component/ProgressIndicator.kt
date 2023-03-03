package com.etu.booking.ui.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Creates content if the enable value is **false**,
 * otherwise creates a [CircularProgressIndicator]
 */
@Composable
fun ProgressIndicator(
    enable: Boolean,
    content: @Composable () -> Unit,
) {
    when (enable) {
        true -> ProgressIndicator()
        false -> content()
    }
}
