package com.etu.booking.viewmodel

import com.etu.booking.R
import com.etu.booking.data.entity.CredentialEntity
import com.etu.booking.data.repository.CredentialRepository
import com.etu.booking.mapper.toModel
import com.etu.booking.messaging.SnackbarManager
import com.etu.booking.model.CredentialErrorModel
import com.etu.booking.model.CredentialModel
import com.etu.booking.utils.authorized
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update

class CredentialViewModel(
    private val snackbarManager: SnackbarManager,
    private val credentialRepository: CredentialRepository,
) : ViewModelWithLoading() {

    private val _credentialState = MutableStateFlow<CredentialModel?>(null)
    private val _errorModel = MutableStateFlow(getResetErrorModel())

    val credentialState = _credentialState.asStateFlow()
    val errorModel = _errorModel.asStateFlow()

    fun signUp(login: String, password: String) = launchWithLoading {
        val credentialEntityFromDb = credentialRepository.findByLogin(login)
            .firstOrNull()

        if (credentialEntityFromDb == null) {
            val credentialEntity = CredentialEntity(
                login = login,
                password = password,
            )

            _credentialState.update {
                credentialRepository.insert(credentialEntity)
                credentialEntity.toModel()
            }
            _errorModel.update { getResetErrorModel() }
            snackbarManager.showMessage(R.string.authorization_toggle_on)
        } else {
            _errorModel.update { getResetErrorModel().copy(alreadyExists = true) }
        }
    }

    fun signIn(login: String, password: String) = launchWithLoading {
        val credentialModel = credentialRepository.findByLoginAndPassword(login, password)
            .firstOrNull()
            ?.toModel()

        if (credentialModel.authorized()) {
            _credentialState.update { credentialModel }
            _errorModel.update { getResetErrorModel() }
            snackbarManager.showMessage(R.string.authorization_toggle_on)
        } else {
            _errorModel.update { getResetErrorModel().copy(notFound = true) }
        }
    }

    fun signOut() {
        _credentialState.update { null }
        _errorModel.update { getResetErrorModel() }
        snackbarManager.showMessage(R.string.authorization_toggle_off)
    }

    private fun getResetErrorModel() =
        CredentialErrorModel(
            alreadyExists = false,
            notFound = false,
        )
}
