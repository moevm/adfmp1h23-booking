package com.etu.booking.viewmodel

import android.util.Log
import com.etu.booking.constant.BOOKING_LOG_TAG
import com.etu.booking.data.repository.PersonRepository
import com.etu.booking.dependecyinjection.provider.CredentialProvider
import com.etu.booking.mapper.toEntity
import com.etu.booking.mapper.toModel
import com.etu.booking.model.AuthModel
import com.etu.booking.model.PersonModel
import com.etu.booking.utils.authorized
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val personRepository: PersonRepository,
) : ViewModelWithLoading() {

    private val _person = MutableStateFlow<PersonModel?>(null).apply { drop(1) }

    val person = _person.asStateFlow()

    fun updatePerson() = launchWithLoading {
        _person.update {
            personRepository.findById(CredentialProvider.getId().toString())
                .firstOrNull()?.toModel()
        }
    }

    fun insertPerson(
        authModel: AuthModel,
    ) = launchWithLoading {
        if (!CredentialProvider.getCredential().authorized()) {
            Log.e(BOOKING_LOG_TAG, "Not authorized to insert personal information")
            return@launchWithLoading
        }

        val personId = CredentialProvider.getId()

        val personModel = PersonModel(
            id = personId,
            login = authModel.login,
            password = authModel.password,
            name = authModel.personModel.name,
            surname = authModel.personModel.surname,
            birthdate = authModel.personModel.birthdate,
            avatarResource = authModel.personModel.avatarResource,
            passport = PersonModel.PassportModel(
                nationality = authModel.personModel.passport.nationality,
                number = authModel.personModel.passport.number,
                expiresAt = authModel.personModel.passport.expiresAt,
            ),
            seenDocumentWarning = false,
        )

        val personEntityFromDb = personRepository.findById(personId.toString())
            .firstOrNull()

        if (personEntityFromDb == null) {
            personRepository.insert(personModel.toEntity())
        } else {
            Log.e(BOOKING_LOG_TAG, "Person with id = $personId already exists")
        }
    }
}
