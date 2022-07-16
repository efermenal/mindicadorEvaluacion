package com.example.mindicadorevaluation.features

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.setSupportActionBarTitle(@StringRes title: Int) {
    supportActionBar?.title = getString(title)
}

fun AppCompatActivity.setSupportActionBarTitle(@StringRes title: Int, vararg args: Any) {
    supportActionBar?.title = getString(title, *args)
}
