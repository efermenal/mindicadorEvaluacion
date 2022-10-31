package com.example.mindicadorevaluation.core.crypto

import javax.inject.Inject


class Base64CipherImpl @Inject constructor() : Base64Cipher {
    override fun encode(arrayToEncode: ByteArray): ByteArray? {
        return android.util.Base64.encode(arrayToEncode, android.util.Base64.DEFAULT)
    }

    override fun decode(stringToDecode: String): ByteArray {
        return android.util.Base64.decode(stringToDecode, android.util.Base64.DEFAULT)
    }
}
