package com.example.mindicadorevaluation.features

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setSupportActionBarTitle(@StringRes title: Int) {
    if (activity is AppCompatActivity) {
        (activity as AppCompatActivity).setSupportActionBarTitle(title)
    }
}

fun Fragment.setSupportActionBarTitle(@StringRes title: Int, vararg args: Any) {
    if (activity is AppCompatActivity) {
        (activity as AppCompatActivity).setSupportActionBarTitle(title, *args)
    }
}
