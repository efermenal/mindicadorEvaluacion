package com.example.mindicadorevaluation.features.login


import androidx.lifecycle.ViewModel
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.db.UserDao
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(
    private val encryption : Encryption,
    private val userDao : UserDao
)  : ViewModel() {


    fun attemptingLogin(id : String, password : String){
        Timber.d("$id $password")
        val passCipher = encryption.encode(password)
       Timber.d("passCipher $passCipher")
    }

}