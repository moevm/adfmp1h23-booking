package com.etu.booking.view

import androidx.lifecycle.ViewModel
import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.model.AuthModel
import com.etu.booking.model.PersonModel
import com.etu.booking.model.PersonModel.PassportModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class AuthViewModel(
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _authState = MutableStateFlow(
        AuthModel(
            login = "",
            password = "",
            personModel = PersonModel(
                login = "",
                password = "",
                name = "",
                surname = "",
                birthdate = LocalDate.now(),
                avatarResource = null,
                passport = PassportModel(
                    number = "",
                    nationality = "",
                    expiresAt = LocalDate.now(),
                )
            )
        )
    )

    val authState: StateFlow<AuthModel> = _authState.asStateFlow()

    fun updateLogin(login: String) {
        _authState.update { it.copy(login = login) }
    }

    fun updatePassword(password: String) {
        _authState.update { it.copy(password = password) }
    }

    fun updateName(name: String) {
        _authState.update { it.copy(personModel = it.personModel.copy(name = name)) }
    }

    fun updateSurname(surname: String) {
        _authState.update { it.copy(personModel = it.personModel.copy(surname = surname)) }
    }

    fun updateBirthdate(birthdate: LocalDate) {
        _authState.update { it.copy(personModel = it.personModel.copy(birthdate = birthdate)) }
    }

    fun updateNationality(nationality: String) {
        _authState.update {
            it.copy(
                personModel = it.personModel.copy(
                    passport = it.personModel.passport.copy(
                        nationality = nationality,
                    )
                )
            )
        }
    }

    fun updatePassportNumber(number: String) {
        _authState.update {
            it.copy(
                personModel = it.personModel.copy(
                    passport = it.personModel.passport.copy(
                        number = number,
                    )
                )
            )
        }
    }

    fun updatePassportExpiresAt(expiresAt: LocalDate) {
        _authState.update {
            it.copy(
                personModel = it.personModel.copy(
                    passport = it.personModel.passport.copy(
                        expiresAt = expiresAt
                    )
                )
            )
        }
    }

    fun updateAvatarResource(avatarResource: Int?) {
        _authState.update {
            it.copy(personModel = it.personModel.copy(avatarResource = avatarResource))
        }
    }
}
