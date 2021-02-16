package com.example.mindicadorevaluation.features.login


import androidx.lifecycle.ViewModel
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.db.UserDao
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(
    private val serviceEncryption : Encryption,
    private val userDao : UserDao
)  : ViewModel() {


    fun attemptingLogin(id : String, password : String){

        Timber.d(serviceEncryption.encode(password))

    }

}