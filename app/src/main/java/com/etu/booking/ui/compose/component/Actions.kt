package com.etu.booking.ui.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material.icons.twotone.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etu.booking.R

@Composable
fun SuccessAction(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Action(
        text = text,
        icon = Icons.TwoTone.CheckCircle,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
fun FailedAction(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Action(
        modifier = modifier,
        text = text,
        icon = Icons.TwoTone.Warning,
        onClick = onClick,
    )
}

@Composable
private fun Action(
    modifier: Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = icon.name,
            modifier = Modifier.size(150.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        PushButton(text = stringResource(id = R.string.ok), onClick = onClick)
    }
}
