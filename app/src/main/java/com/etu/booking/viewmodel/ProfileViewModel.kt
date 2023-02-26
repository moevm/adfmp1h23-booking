package com.etu.booking.viewmodel

import com.etu.booking.default.DefaultModels.PERSON_MODEL
import com.etu.booking.model.PersonModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModelWithLoading() {

    private val _person = MutableStateFlow<PersonModel?>(null).apply { drop(1) }

    val person = _person.asStateFlow()

    fun updateProfile() = launchWithLoading {
        _person.update { PERSON_MODEL }
    }
}