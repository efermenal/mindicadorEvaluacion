package com.example.mindicadorevaluation.core.crypto

/*
[android.util.Base64] works on API >= 8. So its implementation is used in the app.
On the other hand,  Base64.getEncoder()/Base64.getDecoder() depends on higher API.
So, I'm using the last one in test (both of them generate equal results)
 */
interface Base64Cipher {
    fun encode(arrayToEncode: ByteArray): ByteArray?
    fun decode(stringToDecode: String): ByteArray
}
