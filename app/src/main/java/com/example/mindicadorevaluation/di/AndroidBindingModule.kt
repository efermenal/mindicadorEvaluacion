package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.Repositories.RemoteRepositoryImpl
import com.example.mindicadorevaluation.core.crypto.*
import com.example.mindicadorevaluation.core.services.NetworkInformation
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.NetworkState
import com.example.mindicadorevaluation.features.detail.DetailActivity
import com.example.mindicadorevaluation.features.login.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun mainActivity() : MainActivity

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun detailActivity() : DetailActivity

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