package com.etu.booking.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.R

@Composable
fun NothingToDisplay(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.empty_space),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

/**
 * Creates content if the enable value is **true**,
 * otherwise creates a stub for the void
 */
@Composable
fun NothingToDisplay(
    modifier: Modifier = Modifier,
    enable: Boolean,
    content: @Composable () -> Unit,
) {
    when (enable) {
        true -> NothingToDisplay(modifier = modifier)
        false -> content()
    }
}
