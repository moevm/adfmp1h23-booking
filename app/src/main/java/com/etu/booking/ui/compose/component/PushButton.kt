package com.etu.booking.ui.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PushButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            disabledBackgroundColor = Color.LightGray
        )
    ) {
        Text(
            text = text,
            color = Color.Black
        )
    }
}