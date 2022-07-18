package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.core.utils.DefaultDispatcherProvider
import com.example.mindicadorevaluation.core.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DispatcherModule {

    @Singleton
    @Provides
    fun providesDispatcher(): DispatcherProvider = DefaultDispatcherProvider()
}
