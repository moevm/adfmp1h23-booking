package com.etu.booking.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.etu.booking.model.AuthModel
import com.etu.booking.model.PersonModel
import com.etu.booking.model.PersonModel.PassportModel
import java.time.LocalDate

class AuthViewModel : ViewModel()  {

    private var authModel = AuthModel(
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

    var login by mutableStateOf(authModel.login)
    var password by mutableStateOf(authModel.password)
    var name by mutableStateOf(authModel.personModel.name)
    var surname by mutableStateOf(authModel.personModel.surname)
    var birthdate by mutableStateOf(authModel.personModel.birthdate)
    var avatarResource by mutableStateOf(authModel.personModel.avatarResource)
    var passport by mutableStateOf(authModel.personModel.passport)

}