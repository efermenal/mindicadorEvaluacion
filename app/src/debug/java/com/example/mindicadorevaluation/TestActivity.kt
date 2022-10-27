package com.example.mindicadorevaluation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

class TestActivity : AppCompatActivity(), HasAndroidInjector {

    private lateinit var injector: AndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = injector

    fun startFragment(fragment: Fragment, injector: AndroidInjector<Any>) {
        this.injector = injector
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, fragment, "TAG")
            .commit()
    }

    inline fun <reified T : Fragment> startFragment(
        fragment: T,
        crossinline injector: (T) -> Unit
    ) {
        startFragment(fragment, AndroidInjector { if (it is T) injector(it) })
    }

}
