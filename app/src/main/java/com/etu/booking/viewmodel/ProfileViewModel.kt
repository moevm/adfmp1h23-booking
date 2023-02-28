package com.etu.booking.viewmodel

import com.etu.booking.default.DefaultModels
import com.etu.booking.default.DefaultModels.PERSON_MODEL
import com.etu.booking.model.AuthModel
import com.etu.booking.model.PersonModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModelWithLoading() {

    private val personsDatabase = mutableListOf(PERSON_MODEL)

    private val _person = MutableStateFlow<PersonModel?>(null).apply { drop(1) }

    val person = _person.asStateFlow()

    fun updateProfile() = launchWithLoading {
        _person.update { personsDatabase.first() }
    }

    fun updateProfileFromAuthState(
        authModel: AuthModel,
    ) = launchWithLoading { // TODO: temporary, it will be removed
        val personModel = authModel.personModel
        val passportModel = authModel.personModel.passport
        val newPerson = PersonModel(
            login = authModel.login,
            password = authModel.password,
            name = authModel.personModel.name,
            surname = personModel.surname,
            birthdate = personModel.birthdate,
            avatarResource = personModel.avatarResource ?: DefaultModels.DEFAULT_AVATAR,
            passport = PersonModel.PassportModel(
                nationality = passportModel.nationality,
                number = passportModel.number,
                expiresAt = passportModel.expiresAt,
            ),
        )

        _person.update {
            personsDatabase[0] = newPerson
            newPerson
        }
    }
}
