package com.example.mindicadorevaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.core.services.NetworkInformation
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.DispatcherProvider
import com.example.mindicadorevaluation.db.UserDao
import com.example.mindicadorevaluation.features.detail.ListIndicatorViewModel
import com.example.mindicadorevaluation.features.login.LoginViewModel
import org.mockito.kotlin.mock

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryTest(
    private val dispatcherProvider: DispatcherProvider,
    private val encryption: Encryption = mock(),
    private val userDao: UserDao = mock(),
    private val auth: Authenticator = mock(),
    private val remote: RemoteRepository = mock(),
    private val netInfo: NetworkInformation = mock(),
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                encryption,
                userDao,
                dispatcherProvider,
                auth
            ) as T
        }

        if (modelClass.isAssignableFrom(ListIndicatorViewModel::class.java)) {
            return ListIndicatorViewModel(remote, netInfo, auth) as T
        }

        throw IllegalArgumentException("Cannot return viewModel")

    }
}
