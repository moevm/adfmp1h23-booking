package com.etu.booking.compose.screen

import android.content.Intent
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.etu.booking.DocumentActivity
import com.etu.booking.R
import com.etu.booking.default.DefaultModels
import com.etu.booking.model.PersonModel
import com.etu.booking.view.AuthorizationViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ProfileScreen(authorizationViewModel: AuthorizationViewModel) {
    val authorizationState by authorizationViewModel.authorizationState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ProfileTopBar()
        if (authorizationState.isAuthorized) {
            PersonInfo(DefaultModels.PERSON_MODEL)
            PassportInfo(DefaultModels.PERSON_MODEL)
            ProfileButtons()
        } else {
            UnauthorizedScreen()
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
fun PersonInfo(personModel: PersonModel) {
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
fun PassportInfo(personModel: PersonModel) {
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
fun InfoText(info: String, text: String) {
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
fun ProfileButtons() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        ProfileButton(Icons.TwoTone.AccountBox, "Add document") {
            // TODO: add on click handle for adding document
        }
        ProfileButton(Icons.TwoTone.AccountBox, "Show documents") {
            context.startActivity(Intent(context, DocumentActivity::class.java))
        }
    }
}

@Composable
fun ProfileButton(
    image: ImageVector,
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
