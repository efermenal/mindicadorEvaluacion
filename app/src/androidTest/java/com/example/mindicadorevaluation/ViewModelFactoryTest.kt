package com.example.mindicadorevaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.core.utils.DispatcherProvider
import com.example.mindicadorevaluation.db.UserDao
import com.example.mindicadorevaluation.features.login.LoginViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryTest(
    private val encryption: Encryption,
    private val userDao: UserDao,
    private val dispatcherProvider: DispatcherProvider,
    private val auth: Authenticator,
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
       return LoginViewModel(
           encryption,
           userDao,
           dispatcherProvider,
           auth
       ) as T
    }
}
