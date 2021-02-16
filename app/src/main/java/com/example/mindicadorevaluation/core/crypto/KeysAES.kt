package com.example.mindicadorevaluation.core.crypto

import javax.inject.Inject

class KeysAES @Inject constructor() : KeysRepository {
    override fun getSecretKey(): String {
        return "estoesunasuperasswordparaprobarl"
    }
}