package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.Repositories.RemoteRepositoryImpl
import com.example.mindicadorevaluation.core.crypto.Base64Cipher
import com.example.mindicadorevaluation.core.crypto.Base64CipherImpl
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.core.crypto.EncryptionAES
import com.example.mindicadorevaluation.core.crypto.KeysAES
import com.example.mindicadorevaluation.core.crypto.KeysRepository
import com.example.mindicadorevaluation.core.services.NetworkInformation
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.NetworkState
import dagger.Binds
import dagger.Module

@Module
abstract class AndroidBindingModule {

    @Binds
    abstract  fun bindRemoteRepository(remote : RemoteRepositoryImpl) : RemoteRepository

    @Binds
    abstract  fun bindEncryptionAES(encryption : EncryptionAES) : Encryption

    @Binds
    abstract  fun bindKeysAES(keysAES : KeysAES) : KeysRepository

    @Binds
    abstract  fun bindBase64Cipher(base64CipherImpl : Base64CipherImpl) : Base64Cipher

    @Binds
    abstract fun bindNetWorkState(networkState: NetworkState) : NetworkInformation

}
