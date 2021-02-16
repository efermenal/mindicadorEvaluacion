package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.Repositories.RemoteRepositoryImpl
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.core.crypto.EncryptionAES
import com.example.mindicadorevaluation.core.crypto.KeysAES
import com.example.mindicadorevaluation.core.crypto.KeysRepository
import com.example.mindicadorevaluation.core.services.RemoteRepository
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

}