package com.example.mindicadorevaluation.core.crypto

import javax.inject.Inject

class KeysAES @Inject constructor() : KeysRepository {
    override fun getSecretKey(): String {
        /*this return can changed by a declaration from gradle.properties.
        * in that way yo do not expose the key in the repository and it is safer
        *  */
        return "estoesunasuperasswordparaprobarl"
    }
}