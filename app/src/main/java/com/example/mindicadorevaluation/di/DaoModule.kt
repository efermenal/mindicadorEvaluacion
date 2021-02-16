package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.db.MindicadorDatabase
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

    @Provides
    fun provideIndicatorDao(database: MindicadorDatabase)  = database.getIndicatorDao()
}