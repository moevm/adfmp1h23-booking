package com.etu.booking.dependecyinjection.provider

import com.etu.booking.model.CredentialModel
import kotlinx.coroutines.flow.StateFlow
import java.util.*

object CredentialProvider {
    private var credentialState: StateFlow<CredentialModel?>? = null

    fun setCredentialState(newCredentialState: StateFlow<CredentialModel?>) {
        credentialState = newCredentialState
    }

    fun getCredential(): CredentialModel? =
        credentialState?.value

    fun getId(): UUID? =
        getCredential()?.id
}
