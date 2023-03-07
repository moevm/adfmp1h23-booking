package com.etu.booking.ui.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.etu.booking.R
import com.etu.booking.model.AuthModel
import com.etu.booking.model.CredentialErrorModel
import com.etu.booking.ui.compose.component.ErrorText
import com.etu.booking.ui.compose.component.Input
import com.etu.booking.ui.compose.component.ProgressIndicator
import com.etu.booking.ui.compose.component.PushButton
import com.etu.booking.utils.authorized
import com.etu.booking.viewmodel.AuthViewModel
import com.etu.booking.viewmodel.CredentialViewModel
import com.etu.booking.viewmodel.ProfileViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    credentialViewModel: CredentialViewModel,
    onAuthorized: () -> Unit,
    onAddDocumentClick: () -> Unit,
) {
    val authState by authViewModel.authState.collectAsState()
    val credentialErrorModel by credentialViewModel.errorModel.collectAsState()
    val credentialState by credentialViewModel.credentialState.collectAsState()
    val isLoading by credentialViewModel.isLoading.collectAsState()

    var state by remember { mutableStateOf(0) }
    val titles = listOf("Sign In", "Sign Up")

    ProgressIndicator(enable = isLoading) {
        if (credentialState.authorized()) {
            onAuthorized()
        } else {
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
                        credentialErrorModel = credentialErrorModel,
                        onLoginChange = authViewModel::updateLogin,
                        onPasswordChange = authViewModel::updatePassword,
                        onSignInClick = {
                            if (isSignInEnable(authState)) {
                                credentialViewModel.signIn(authState.login, authState.password)
                            } else {
                                authViewModel.highlightInputs()
                            }
                        },
                    )
                } else {
                    SignUp(
                        authState = authState,
                        credentialErrorModel = credentialErrorModel,
                        onNameChange = authViewModel::updateName,
                        onSurnameChange = authViewModel::updateSurname,
                        onBirthdateChange = authViewModel::updateBirthdate,
                        onNationalityChange = authViewModel::updateNationality,
                        onPassportNumberChange = authViewModel::updatePassportNumber,
                        onExpiresAtChange = authViewModel::updatePassportExpiresAt,
                        onLoginChange = authViewModel::updateLogin,
                        onPasswordChange = authViewModel::updatePassword,
                        onAddDocumentClick = onAddDocumentClick,
                        onSignUpClick = {
                            if (isSignUpEnable(authState)) {
                                credentialViewModel.signUp(authState.login, authState.password)
                                    .invokeOnCompletion { profileViewModel.insertPerson(authState) }
                            } else {
                                authViewModel.highlightInputs()
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun SignIn(
    authState: AuthModel,
    credentialErrorModel: CredentialErrorModel,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

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
        if (credentialErrorModel.notFound) {
            ErrorText(text = stringResource(id = R.string.user_not_found))
        }
        Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.login,
            placeholder = "Login",
            onChange = onLoginChange,
            imeAction = ImeAction.Next,
            isError = authState.errorModel.login,
            errorMessage = stringResource(id = R.string.login_error_message)
        )
        Input(
            modifier = Modifier.fillMaxWidth(),
            text = authState.password,
            placeholder = "Password",
            onChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            imeAction = ImeAction.Done,
            keyBoardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = authState.errorModel.password,
            errorMessage = stringResource(id = R.string.password_error_message)
        )
        PushButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign In",
            onClick = onSignInClick
        )
    }
}

@Composable
private fun SignUp(
    authState: AuthModel,
    credentialErrorModel: CredentialErrorModel,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onBirthdateChange: (LocalDate) -> Unit,
    onNationalityChange: (String) -> Unit,
    onPassportNumberChange: (String) -> Unit,
    onExpiresAtChange: (LocalDate) -> Unit,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onAddDocumentClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    val context = LocalContext.current
    val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val focusManager = LocalFocusManager.current

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
        item {
            if (credentialErrorModel.alreadyExists) {
                ErrorText(text = stringResource(id = R.string.user_already_exists))
            }
        }
        item {
            Input(
                modifier = Modifier.fillMaxWidth(),
                text = authState.personModel.name,
                placeholder = "Name",
                onChange = onNameChange,
                imeAction = ImeAction.Next,
                isError = authState.errorModel.name,
                errorMessage = stringResource(id = R.string.proper_name_error_message)
            )
        }
        item {
            Input(
                modifier = Modifier.fillMaxWidth(),
                text = authState.personModel.surname,
                placeholder = "Surname",
                onChange = onSurnameChange,
                imeAction = ImeAction.Next,
                isError = authState.errorModel.surname,
                errorMessage = stringResource(id = R.string.proper_name_error_message)
            )
        }
        item {
            Input(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDatePickerDialog(
                            context = context,
                            date = authState.personModel.birthdate,
                            onChange = onBirthdateChange,
                        )
                    },
                text = dateFormat.format(authState.personModel.birthdate),
                placeholder = "Birthdate",
                onChange = { onBirthdateChange(LocalDate.parse(it, dateFormat)) },
                isEnabled = false,
                imeAction = ImeAction.Next,
                isError = authState.errorModel.birthdate,
                errorMessage = stringResource(id = R.string.not_future_date_error_message)
            )
        }
        item {
            Input(
                modifier = Modifier.fillMaxWidth(),
                text = authState.personModel.passport.nationality,
                placeholder = "Nationality",
                onChange = onNationalityChange,
                imeAction = ImeAction.Next,
                isError = authState.errorModel.nationality,
                errorMessage = stringResource(id = R.string.proper_name_error_message)
            )
        }
        item {
            Input(
                modifier = Modifier.fillMaxWidth(),
                text = authState.personModel.passport.number,
                placeholder = "Passport number",
                onChange = onPassportNumberChange,
                imeAction = ImeAction.Next,
                isError = authState.errorModel.passportNumber,
                errorMessage = stringResource(id = R.string.passport_number_error_message)
            )
        }
        item {
            Input(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDatePickerDialog(
                            context = context,
                            date = authState.personModel.passport.expiresAt,
                            onChange = onExpiresAtChange
                        )
                    },
                text = dateFormat.format(authState.personModel.passport.expiresAt),
                placeholder = "Expires at",
                onChange = { onExpiresAtChange(LocalDate.parse(it, dateFormat)) },
                isEnabled = false,
                imeAction = ImeAction.Next,
                isError = authState.errorModel.passportExpiresAt,
                errorMessage = stringResource(id = R.string.not_past_date_error_message)
            )
        }
        item {
            Input(
                modifier = Modifier.fillMaxWidth(),
                text = authState.login,
                placeholder = "Login",
                onChange = onLoginChange,
                imeAction = ImeAction.Next,
                isError = authState.errorModel.login,
                errorMessage = stringResource(id = R.string.login_error_message)
            )
        }
        item {
            Input(
                modifier = Modifier.fillMaxWidth(),
                text = authState.password,
                placeholder = "Password",
                onChange = onPasswordChange,
                visualTransformation = PasswordVisualTransformation(),
                imeAction = ImeAction.Done,
                keyBoardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                isError = authState.errorModel.password,
                errorMessage = stringResource(id = R.string.password_error_message)
            )
        }
        item {
            PushButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Add document photo",
                onClick = onAddDocumentClick
            )
        }
        item {
            PushButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Sign Up",
                onClick = onSignUpClick
            )
        }
    }
}

private fun isSignInEnable(authModel: AuthModel): Boolean = authModel.run {
    login != ""
            && password != ""
            && !errorModel.login
            && !errorModel.password
}

private fun isSignUpEnable(authModel: AuthModel): Boolean = authModel.run {
    login != ""
            && password != ""
            && personModel.name != ""
            && personModel.surname != ""
            && personModel.passport.nationality != ""
            && personModel.passport.number != ""
            && !errorModel.login
            && !errorModel.password
            && !errorModel.name
            && !errorModel.surname
            && !errorModel.birthdate
            && !errorModel.nationality
            && !errorModel.passportNumber
            && !errorModel.passportExpiresAt
}
