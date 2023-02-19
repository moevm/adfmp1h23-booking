package com.etu.booking.compose.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etu.booking.view.AuthViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Sign In", "Sign Up")

    Column {
        TabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(text = title)
                    },
                    selected = state == index,
                    onClick = {
                        state = index
                    }
                )
            }
        }
        if (state == 0) {
            SignIn(viewModel)
        } else {
            SignUp(viewModel)
        }
    }
}

@Composable
fun SignIn(
    viewModel: AuthViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.login,
            placeholder = "Login",
            onValueChange = {
                viewModel.login = it
            },
        )
        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.password,
            placeholder = "Password",
            onValueChange = {
                viewModel.password = it
            },
            visualTransformation = PasswordVisualTransformation(),
        )
        AuthButton(text = "Sign In") {
            // TODO: sign in
        }
    }
}

@Composable
fun SignUp(
    viewModel: AuthViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.name,
            placeholder = "Name",
            onValueChange = {
                viewModel.name = it
            },
        )
        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.surname,
            placeholder = "Surname",
            onValueChange = {
                viewModel.surname = it
            },
        )
        AuthInput(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePickerDialog(context, viewModel.birthdate) {
                        date -> viewModel.birthdate = date
                    }
                },
            value = dateFormat.format(viewModel.birthdate),
            placeholder = "Birthdate",
            onValueChange = {
                viewModel.birthdate = LocalDate.parse(it, dateFormat)
            },
            isEnabled = false,
        )
        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.passport.nationality,
            placeholder = "Nationality",
            onValueChange = {
                viewModel.passport.nationality = it
            },
        )
        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.passport.number,
            placeholder = "Passport number",
            onValueChange = {
                viewModel.passport.number = it
            },
        )
        AuthInput(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePickerDialog(context, viewModel.passport.expiresAt) {
                            date -> viewModel.birthdate = date
                    }
                },
            value = dateFormat.format(viewModel.passport.expiresAt),
            placeholder = "Expires at",
            onValueChange = {
                viewModel.passport.expiresAt = LocalDate.parse(it, dateFormat)
            },
            isEnabled = false,
        )
        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.login,
            placeholder = "Login",
            onValueChange = {
                viewModel.login = it
            },
        )
        AuthInput(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.password,
            placeholder = "Password",
            onValueChange = {
                viewModel.password = it
            },
            visualTransformation = PasswordVisualTransformation(),
        )
        AuthButton(text = "Add document photo") {
            // TODO: get access to camera or gallery
        }
        AuthButton(text = "Sign Up") {
            // TODO: sign up
        }
    }
}

@Composable
fun AuthInput(
    modifier: Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isEnabled: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        placeholder = {
            Text(text = placeholder, style = TextStyle(fontSize = 18.sp, color = Color.LightGray))
        },
        enabled = isEnabled,
    )
}

@Composable
fun AuthButton(
    text: String, 
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text)
        }
    }
}
