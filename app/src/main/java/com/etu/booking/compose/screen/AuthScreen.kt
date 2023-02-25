package com.etu.booking.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.etu.booking.R
import com.etu.booking.compose.component.Input
import com.etu.booking.compose.component.PushButton
import com.etu.booking.model.AuthModel
import com.etu.booking.view.AuthViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    val authState = viewModel.authState.collectAsState()

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
            SignIn(
                authState = authState,
                onLoginChange = viewModel::updateLogin,
                onPasswordChange = viewModel::updatePassword,
            )
        } else {
            SignUp(
                authState = authState,
                onNameChange = viewModel::updateName,
                onSurnameChange = viewModel::updateSurname,
                onBirthdateChange = viewModel::updateBirthdate,
                onNationalityChange = viewModel::updateNationality,
                onPassportNumberChange = viewModel::updatePassportNumber,
                onExpiresAtChange = viewModel::updatePassportExpiresAt,
                onLoginChange = viewModel::updateLogin,
                onPasswordChange = viewModel::updatePassword,
            )
        }
    }
}

@Composable
private fun SignIn(
    authState: State<AuthModel>,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
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
        Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.value.login,
            placeholder = "Login",
            onChange = onLoginChange,
            isError = authState.value.errorModel.login,
            errorMessage = stringResource(id = R.string.login_error_message)
        )
        Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.value.password,
            placeholder = "Password",
            onChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            isError = authState.value.errorModel.password,
            errorMessage = stringResource(id = R.string.password_error_message)
        )
        PushButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign In",
            onClick = {
                // TODO: sign in
            }
        )
    }
}

@Composable
private fun SignUp(
    authState: State<AuthModel>,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onBirthdateChange: (LocalDate) -> Unit,
    onNationalityChange: (String) -> Unit,
    onPassportNumberChange: (String) -> Unit,
    onExpiresAtChange: (LocalDate) -> Unit,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    val context = LocalContext.current
    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.value.personModel.name,
            placeholder = "Name",
            onChange = onNameChange,
            isError = authState.value.errorModel.name,
            errorMessage = stringResource(id = R.string.proper_name_error_message)
        ) }
        item { Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.value.personModel.surname,
            placeholder = "Surname",
            onChange = onSurnameChange,
            isError = authState.value.errorModel.surname,
            errorMessage = stringResource(id = R.string.proper_name_error_message)
        ) }
        item { Input(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePickerDialog(
                        context = context,
                        date = authState.value.personModel.birthdate,
                        onChange = onBirthdateChange,
                    )
                },
            text = dateFormat.format(authState.value.personModel.birthdate),
            placeholder = "Birthdate",
            onChange = { onBirthdateChange(LocalDate.parse(it, dateFormat)) },
            isEnabled = false,
            isError = authState.value.errorModel.birthdate,
            errorMessage = stringResource(id = R.string.not_future_date_error_message)
        ) }
        item { Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.value.personModel.passport.nationality,
            placeholder = "Nationality",
            onChange = onNationalityChange,
            isError = authState.value.errorModel.nationality,
            errorMessage = stringResource(id = R.string.proper_name_error_message)
        ) }
        item { Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.value.personModel.passport.number,
            placeholder = "Passport number",
            onChange = onPassportNumberChange,
            isError = authState.value.errorModel.passportNumber,
            errorMessage = stringResource(id = R.string.passport_number_error_message)
        ) }
        item { Input(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDatePickerDialog(
                        context = context,
                        date = authState.value.personModel.passport.expiresAt,
                        onChange = onExpiresAtChange
                    )
                },
            text = dateFormat.format(authState.value.personModel.passport.expiresAt),
            placeholder = "Expires at",
            onChange = { onExpiresAtChange(LocalDate.parse(it, dateFormat)) },
            isEnabled = false,
            isError = authState.value.errorModel.passportExpiresAt,
            errorMessage = stringResource(id = R.string.not_past_date_error_message)
        ) }
        item { Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.value.login,
            placeholder = "Login",
            onChange = onLoginChange,
            isError = authState.value.errorModel.login,
            errorMessage = stringResource(id = R.string.login_error_message)
        ) }
        item { Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.value.password,
            placeholder = "Password",
            onChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            isError = authState.value.errorModel.password,
            errorMessage = stringResource(id = R.string.password_error_message)
        ) }
        item { PushButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Add document photo",
            onClick = {
                // TODO: get access to camera or gallery
            }
        ) }
        item { PushButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign Up",
            onClick = {
                // TODO: sign up
            }
        ) }
    }
}
