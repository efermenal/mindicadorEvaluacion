package com.example.mindicadorevaluation.core.services

import javax.inject.Inject

class AuthenticatorImpl @Inject
constructor(): Authenticator  {
    var userName : String = ""
    var isUserLogged : Boolean = false

    override fun setIsLogged(isLogged: Boolean) {
        isUserLogged = isLogged
    }

    override fun isUserLoggedIn(): Boolean = isUserLogged

    override fun getUserLogged(): String = userName

    override fun setUserLogged(name: String) {
        userName = name
    }
}