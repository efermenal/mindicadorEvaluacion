package com.example.mindicadorevaluation.core.services

import javax.inject.Inject

class AuthenticatorImpl @Inject constructor() : Authenticator {
    private var userName: String = ""
    private var isUserLogged: Boolean = false

    override fun setIsLogged(isLogged: Boolean) {
        isUserLogged = isLogged
        if (isLogged.not()) {
            setUserLogged("")
        }
    }

    override fun isUserLoggedIn(): Boolean = isUserLogged

    override fun getUserLogged(): String = userName

    override fun setUserLogged(name: String) {
        userName = name
    }
}
