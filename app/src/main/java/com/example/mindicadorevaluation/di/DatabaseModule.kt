package com.example.mindicadorevaluation.di

import android.content.Context
import androidx.room.Room
import com.example.mindicadorevaluation.db.MindicadorDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : MindicadorDatabase{
        return Room.databaseBuilder(context, MindicadorDatabase::class.java, "mindicador.db")
            .build()
    }

}