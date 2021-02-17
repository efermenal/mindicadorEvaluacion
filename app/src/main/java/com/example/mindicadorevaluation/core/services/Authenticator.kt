package com.example.mindicadorevaluation.core.services

/*
For this test this interface is only used for show the name of the user.
And the user is never change to off because the flow does not depend on it.
Nevertheless the user is updated in every login
 */

interface Authenticator {
    fun setIsLogged(isLogged : Boolean)
    fun isUserLoggedIn() : Boolean
    fun getUserLogged() : String
    fun setUserLogged(name : String)
}