package com.example.mindicadorevaluation.features

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setSupportActionBarTitle(@StringRes title: Int) {
    (activity as AppCompatActivity).setSupportActionBarTitle(title)
}

fun Fragment.setSupportActionBarTitle(@StringRes title: Int, vararg args: Any) {
    (activity as AppCompatActivity).setSupportActionBarTitle(title, *args)
}
