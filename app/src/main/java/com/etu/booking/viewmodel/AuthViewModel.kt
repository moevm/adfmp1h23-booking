package com.etu.booking.viewmodel

import androidx.lifecycle.ViewModel
import com.etu.booking.model.AuthErrorModel
import com.etu.booking.model.AuthModel
import com.etu.booking.model.PersonModel
import com.etu.booking.model.PersonModel.PassportModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class AuthViewModel : ViewModel() {

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
            ),
            errorModel = AuthErrorModel()
        )
    )

    val authState: StateFlow<AuthModel> = _authState.asStateFlow()

    private val loginPattern = Regex("[\\w.]{4,30}")
    private val passwordPattern = Regex("\\w{6,30}")
    private val properNamePattern = Regex("[A-Z][a-z]{1,29}")
    private val passportNumberPattern = Regex("[A-Za-z\\d]{1,30}")

    fun updateLogin(login: String) {
        _authState.update { it.copy(
            login = login,
            errorModel = it.errorModel.copy(
                login = !loginPattern.matches(login)
            )
        ) }
    }

    fun updatePassword(password: String) {
        _authState.update { it.copy(
            password = password,
            errorModel = it.errorModel.copy(
                password = !passwordPattern.matches(password)
            )
        ) }
    }

    fun updateName(name: String) {
        _authState.update { it.copy(
            personModel = it.personModel.copy(
                name = name
            ),
            errorModel = it.errorModel.copy(
                name = !properNamePattern.matches(name)
            )
        ) }
        _authState.update { it.copy(personModel = it.personModel.copy(name = name)) }
    }

    fun updateSurname(surname: String) {
        _authState.update { it.copy(
            personModel = it.personModel.copy(
                surname = surname
            ),
            errorModel = it.errorModel.copy(
                surname = !properNamePattern.matches(surname)
            )
        ) }
    }

    fun updateBirthdate(birthdate: LocalDate) {
        val now = LocalDate.now()
        _authState.update { it.copy(
            personModel = it.personModel.copy(
                birthdate = birthdate
            ),
            errorModel = it.errorModel.copy(
                birthdate = now < birthdate
            )
        ) }
    }

    fun updateNationality(nationality: String) {
        _authState.update {
            it.copy(
                personModel = it.personModel.copy(
                    passport = it.personModel.passport.copy(
                        nationality = nationality,
                    )
                ),
                errorModel = it.errorModel.copy(
                    nationality = !properNamePattern.matches(nationality)
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
                ),
                errorModel = it.errorModel.copy(
                    passportNumber = !passportNumberPattern.matches(number)
                )
            )
        }
    }

    fun updatePassportExpiresAt(expiresAt: LocalDate) {
        val now = LocalDate.now()
        _authState.update {
            it.copy(
                personModel = it.personModel.copy(
                    passport = it.personModel.passport.copy(
                        expiresAt = expiresAt
                    )
                ),
                errorModel = it.errorModel.copy(
                    passportExpiresAt = now > expiresAt
                )
            )
        }
    }

    fun highlightInputs() {
        _authState.update {
            val loginValue = _authState.value.login
            val passwordValue = _authState.value.password
            val nameValue = _authState.value.personModel.name
            val surnameValue = _authState.value.personModel.surname
            val nationalityValue = _authState.value.personModel.passport.nationality
            val passportNumberValue = _authState.value.personModel.passport.number

            it.copy(
                errorModel = it.errorModel.copy(
                    login = if (loginValue.isEmpty()) true else it.errorModel.login,
                    password = if (passwordValue.isEmpty()) true else it.errorModel.password,
                    name = if (nameValue.isEmpty()) true else it.errorModel.name,
                    surname = if (surnameValue.isEmpty()) true else it.errorModel.surname,
                    nationality = if (nationalityValue.isEmpty()) true else it.errorModel.nationality,
                    passportNumber = if (passportNumberValue.isEmpty()) true else it.errorModel.passportNumber,
                )
            )
        }
    }
}
