package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.features.login.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun mainActivity(): MainActivity
}
