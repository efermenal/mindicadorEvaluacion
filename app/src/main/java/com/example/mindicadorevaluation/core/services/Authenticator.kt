package com.example.mindicadorevaluation.core.services

interface Authenticator {
    fun setIsLogged(isLogged: Boolean)
    fun isUserLoggedIn(): Boolean
    fun getUserLogged(): String
    fun setUserLogged(name: String)
}
