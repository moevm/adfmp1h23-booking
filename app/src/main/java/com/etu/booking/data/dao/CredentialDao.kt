package com.etu.booking.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.etu.booking.constant.CREDENTIAL_TABLE
import com.etu.booking.data.entity.CredentialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialDao : CrudDao<CredentialEntity> {
    @Query(
        """
            SELECT *
            FROM $CREDENTIAL_TABLE
            WHERE id = :id
        """
    )
    fun findById(id: String): Flow<CredentialEntity>

    @Query(
        """
            SELECT *
            FROM $CREDENTIAL_TABLE
        """
    )
    fun findAll(): Flow<List<CredentialEntity>>

    @Query(
        """
            SELECT *
            FROM $CREDENTIAL_TABLE
            WHERE login = :login
                AND password = :password
        """
    )
    fun findByLoginAndPassword(login: String, password: String): Flow<CredentialEntity>

    @Query(
        """
            SELECT *
            FROM $CREDENTIAL_TABLE
            WHERE login = :login
        """
    )
    fun findByLogin(login: String): Flow<CredentialEntity>
}
