package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.features.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment
}
