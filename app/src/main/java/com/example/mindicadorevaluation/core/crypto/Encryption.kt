package com.example.mindicadorevaluation.core.crypto

interface Encryption {
    fun encode(valueToEncrypt: String): String
    fun decode(valueToDecrypt: String): String
}
