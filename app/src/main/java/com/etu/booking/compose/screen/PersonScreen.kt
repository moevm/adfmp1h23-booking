package com.etu.booking.compose.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountBox
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.etu.booking.default.DefaultModels
import com.etu.booking.model.PersonModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun PersonScreen() {
    Column(
        modifier = Modifier
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            )
            .height(220.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        PersonInfo(DefaultModels.PERSON_MODEL)
        PersonButtons()
    }
}

@Composable
fun PersonInfo(personModel: PersonModel) {
    Row {
        Image(
            painter = painterResource(personModel.avatarResource ?: DefaultModels.DEFAULT_AVATAR),
            contentDescription = "Profile avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Column {
            Text(
                text = personModel.name + " " + personModel.surname,
                style = MaterialTheme.typography.h4
            )
            Text(
                text = getFormattedDate(personModel.birthdate),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
fun PersonButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PersonButton(Icons.TwoTone.AccountBox, "Add document") {
            // TODO: add on click handle for adding document
        }
        PersonButton(Icons.TwoTone.Menu, "History Activity") {
            // TODO: add on click handle for history activity
        }
    }
}

@Composable
fun PersonButton(
    image: ImageVector,
    text: String,
    onClick: () -> Unit,
) {
    androidx.compose.material.Button(
        onClick = onClick,
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = image,
                contentDescription = image.name,
            )
            Text(text = text)
        }
    }
}

private fun getFormattedDate(date: LocalDate): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return date.format(dateTimeFormatter)
}
