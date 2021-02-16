package com.example.mindicadorevaluation.di

import android.app.Application
import com.example.mindicadorevaluation.BaseApplication
import com.example.mindicadorevaluation.di.viewmodels.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    ApplicationModule::class,
    ViewModelModule::class,
    AndroidBindingModule::class,
    DatabaseModule::class
])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        /*
        @BindsInstance
        fun applicationCoroutineScope(@AppCoroutineScope applicationCoroutineScope: CoroutineScope): Builder
        */
        fun build(): AppComponent
    }
}