package com.example.mindicadorevaluation.core.crypto

interface KeysRepository {
    fun getSecretKey() : String
}