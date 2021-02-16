package com.example.mindicadorevaluation.core.crypto

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mindicadorevaluation.core.crypto.EncryptionAES.AESEncyption.decoder
import com.example.mindicadorevaluation.core.crypto.EncryptionAES.AESEncyption.encoder
import timber.log.Timber
import java.nio.charset.Charset
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject


class EncryptionAES @Inject constructor (private val keysRepo : KeysRepository) : Encryption {

    object AESEncyption {
        @RequiresApi(Build.VERSION_CODES.O)
        val encoder = Base64.getEncoder()

        @RequiresApi(Build.VERSION_CODES.O)
        val decoder = Base64.getDecoder()
    }

    private fun cipher(opMode: Int, secretKey: String) : Cipher{
        val c: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(secretKey.toByteArray(), "AES")
        val iv = IvParameterSpec(secretKey.substring(0, 16).toByteArray())
        c.init(opMode, sk, iv)
        return  c
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(IllegalArgumentException::class)
    override fun encode(valueToEncrypt: String) : String {
        if (keysRepo.getSecretKey().length != 32) throw IllegalArgumentException("the secret key must be exactly 32 chars")
            val encrypted = cipher(Cipher.ENCRYPT_MODE, keysRepo.getSecretKey()).doFinal(
                valueToEncrypt.toByteArray(
                    Charset.forName("UTF-8")
                )
            )
           return String(encoder.encode(encrypted))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(IllegalArgumentException::class)
    override fun decode(valueToDecrypt: String): String {
        if (keysRepo.getSecretKey().length != 32) throw IllegalArgumentException("the secret key must be exactly 32 chars")
        val  byteStr = decoder.decode(valueToDecrypt.toByteArray());
        return   String(cipher(Cipher.DECRYPT_MODE, keysRepo.getSecretKey()).doFinal(byteStr), Charset.forName("UTF-8"))
    }
}