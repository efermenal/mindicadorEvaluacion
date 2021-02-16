package com.example.mindicadorevaluation.core.crypto

import java.util.*

class Base64CipherTesting: Base64Cipher {
    override fun encode(arrayToEncode: ByteArray): ByteArray? {
        return Base64.getEncoder().encode(arrayToEncode)
    }

    override fun decode(stringToDecode: String): ByteArray {
        return Base64.getDecoder().decode(stringToDecode)
    }
}