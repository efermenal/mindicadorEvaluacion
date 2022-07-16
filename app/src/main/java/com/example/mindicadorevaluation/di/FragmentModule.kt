package com.example.mindicadorevaluation.di

import com.example.mindicadorevaluation.features.detail.ListIndicatorsFragment
import com.example.mindicadorevaluation.features.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun loginFragment(): LoginFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun listIndicatorFragment(): ListIndicatorsFragment
}
