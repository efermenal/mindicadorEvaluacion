package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.Repositories.RemoteRepositoryImpl
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.features.detail.DetailActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun detailActivity() : DetailActivity

    @Binds
    abstract  fun bindRemoteRepository(remote : RemoteRepositoryImpl) : RemoteRepository

}