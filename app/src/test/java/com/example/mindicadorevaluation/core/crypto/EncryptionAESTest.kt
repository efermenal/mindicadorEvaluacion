package com.example.mindicadorevaluation.core.crypto

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class EncryptionAESTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private
    lateinit var encryption : EncryptionAES

    private lateinit var base64Test : Base64Cipher

    private
    val keys =  mock(KeysRepository::class.java)



    @Before
    fun init(){
        base64Test = Base64CipherTesting()
        encryption = EncryptionAES(keys, base64Test)

        `when`(keys.getSecretKey()).thenReturn("estoesunasuperasswordparaprobarl")
    }

    @Test
    fun encode_shouldReturn_cipherPassword(){
        val password = "endherson"
        /*it is known because the secret key is estoesunasuperasswordparaprobarl*/
        val passwordCiphered = "fgLFr9mgmUqallCHW/tecQ=="

        val encrypted = encryption.encode(password)

        assertThat(encrypted, equalTo(passwordCiphered))
    }

    @Test
    fun decode_shouldReturn_originalPassword(){
        val password = "endherson"

        val encrypted = encryption.encode(password)
        val decrypted = encryption.decode(encrypted)

        assertThat(password, equalTo(decrypted))
    }

    @Test(expected = IllegalArgumentException::class)
    fun encode_when_SecretKey_isShort_ReturnIllegalArgumentException(){
        val password = "endherson"
        `when`(keys.getSecretKey()).thenReturn("a")
        val encrypted = encryption.encode(password)
    }

    @Test(expected = IllegalArgumentException::class)
    fun decode_when_SecretKey_isShort_ReturnIllegalArgumentException(){
        /*it is known because the secret key is estoesunasuperasswordparaprobarl*/
        val passwordCiphered = "fgLFr9mgmUqallCHW/tecQ=="
        `when`(keys.getSecretKey()).thenReturn("a")

        val encrypted = encryption.decode(passwordCiphered)
    }

}