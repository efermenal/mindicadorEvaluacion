package com.example.mindicadorevaluation.di.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mindicadorevaluation.features.detail.ListIndicatorViewModel
import com.example.mindicadorevaluation.features.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListIndicatorViewModel::class)
    abstract fun bindsHomeViewModel(listIndicatorViewModel: ListIndicatorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsLoginViewModel(mainViewModel: LoginViewModel): ViewModel

}
