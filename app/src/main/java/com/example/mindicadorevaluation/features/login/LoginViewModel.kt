package com.example.mindicadorevaluation.features.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.db.UserDao
import com.example.mindicadorevaluation.di.IoDispatcher
import com.example.mindicadorevaluation.features.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val encryption: Encryption,
    private val userDao: UserDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val auth: Authenticator,
) : ViewModel() {

    data class ViewState(
        val isLoading: Boolean = false
    )

    sealed class Command {
        object InvalidUser : Command()
        object EmptyCredentials : Command()
        object NavigateToMainPage : Command()
    }

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    val viewState: LiveData<ViewState> get() = _viewState
    val command: SingleLiveEvent<Command> = SingleLiveEvent()

    private val currentViewState: ViewState
        get() = viewState.value!!

    fun attemptingLogin(id: String, password: String) = viewModelScope.launch {
        withContext(ioDispatcher) {

            if (id.isEmpty() && password.isEmpty()) {
                command.postValue(Command.EmptyCredentials)
                return@withContext
            }
            _viewState.postValue(currentViewState.copy(isLoading = true))

            val users = userDao.getUserById(id).first()

            if (users.isNotEmpty() && encryption.encode(password).trim() == users[0].password.trim()
            ) {
                auth.apply {
                    setIsLogged(true)
                    setUserLogged(users[0].userId)
                }
                Timber.d("User ${users[0].userId}")
                _viewState.postValue(currentViewState.copy(isLoading = false))
                command.postValue(Command.NavigateToMainPage)
            } else {
                _viewState.postValue(currentViewState.copy(isLoading = false))
                command.postValue(Command.InvalidUser)
            }
        }
    }

}
