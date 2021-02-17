package com.example.mindicadorevaluation.core.utils

sealed class ResourceLogin () {
    object Valid : ResourceLogin()
    object  Invalid : ResourceLogin()
    object  Loading : ResourceLogin()
    object  Error : ResourceLogin()
}