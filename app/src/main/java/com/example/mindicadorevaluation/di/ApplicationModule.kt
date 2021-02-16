package com.example.mindicadorevaluation.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApplicationModule{

    @Singleton
    @Binds
    abstract fun bindContext(application: Application): Context

}