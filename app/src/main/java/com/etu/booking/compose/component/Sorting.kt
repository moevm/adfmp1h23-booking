package com.etu.booking.compose.component

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import com.etu.booking.model.filter.SortingType

@Composable
fun SortingButton(
    text: String,
    sortingType: SortingType,
    onClick: () -> Unit,
) {
    OutlinedButton(onClick = onClick) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2
        )
        SortingIcon(sortingType = sortingType)
    }
}

@Composable
private fun SortingIcon(sortingType: SortingType) {
    when (sortingType) {
        SortingType.ASC -> Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = Icons.Filled.KeyboardArrowUp.name,
        )
        SortingType.DESC -> Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = Icons.Filled.KeyboardArrowDown.name,
        )
        else -> Unit
    }
}
