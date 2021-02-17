package com.example.mindicadorevaluation.di

import android.app.Application
import android.content.Context
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.core.services.AuthenticatorImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApplicationModule{

    @Singleton
    @Binds
    abstract fun bindContext(application: Application): Context

    @Singleton
    @Binds
    abstract fun bindAuthenticatorImpl(authenticatorImpl: AuthenticatorImpl) : Authenticator
}