package com.etu.booking.compose.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.etu.booking.R
import com.etu.booking.compose.component.NothingToDisplay
import com.etu.booking.compose.component.ProgressIndicator
import com.etu.booking.default.DefaultModels
import com.etu.booking.model.PersonModel
import com.etu.booking.viewmodel.ProfileViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onAddDocumentClick: () -> Unit,
    onShowDocumentsClick: () -> Unit,
) {
    val isLoading by profileViewModel.isLoading.collectAsState()
    val person by profileViewModel.person.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.updateProfile()
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ProfileTopBar()
        ProgressIndicator(enable = isLoading) {
            NothingToDisplay(enable = (person == null)) {
                PersonInfo(person!!)
                PassportInfo(person!!)
                ProfileButtons(
                    onAddDocumentClick = onAddDocumentClick,
                    onShowDocumentsClick = onShowDocumentsClick,
                )
            }
        }
    }
}

@Composable
private fun ProfileTopBar() {
    TopAppBar {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(id = R.string.profile_screen_title),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Composable
private fun PersonInfo(personModel: PersonModel) {
    Row {
        Image(
            painter = painterResource(personModel.avatarResource ?: DefaultModels.DEFAULT_AVATAR),
            contentDescription = "Profile avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.padding(25.dp)
        ) {
            Text(
                text = "${personModel.name} ${personModel.surname}",
                style = MaterialTheme.typography.h5,
            )
        }
    }
}

@Composable
private fun PassportInfo(personModel: PersonModel) {
    Column(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth(),
    ) {
        InfoText(
            info = "Birthdate:",
            text = getFormattedDate(personModel.birthdate),
        )
        InfoText(
            info = "Nationality:",
            text = personModel.passport.nationality,
        )
        InfoText(
            info = "Number:",
            text = personModel.passport.number,
        )
        InfoText(
            info = "Expires at:",
            text = getFormattedDate(personModel.passport.expiresAt),
        )
    }
}

@Composable
private fun InfoText(info: String, text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 3.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = info,
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 2.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
            )
            Text(
                text = text,
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 2.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
            )
        }
    }

}

@Composable
private fun ProfileButtons(
    onAddDocumentClick: () -> Unit,
    onShowDocumentsClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        ProfileButton(
            icon = Icons.TwoTone.AccountBox,
            text = "Add document",
            onClick = onAddDocumentClick
        )
        ProfileButton(
            icon = Icons.TwoTone.AccountBox,
            text = "Show documents",
            onClick = onShowDocumentsClick,
        )
    }
}

@Composable
private fun ProfileButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        onClick = onClick,
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = icon.name,
            )
            Text(text = text)
        }
    }
}

private fun getFormattedDate(date: LocalDate): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return date.format(dateTimeFormatter)
}
