package com.example.mindicadorevaluation

import android.app.Application
import com.example.mindicadorevaluation.di.AppComponent
import com.example.mindicadorevaluation.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class BaseApplication : HasAndroidInjector, Application() {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var daggerAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        configureDependencyInjection()
    }

    private fun configureDependencyInjection() {
        daggerAppComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        daggerAppComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}