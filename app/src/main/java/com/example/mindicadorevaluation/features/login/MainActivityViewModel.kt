package com.example.mindicadorevaluation.features.login


import androidx.lifecycle.*
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.core.models.User
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.core.services.NetworkInformation
import com.example.mindicadorevaluation.core.utils.ResourceLogin
import com.example.mindicadorevaluation.db.UserDao
import com.example.mindicadorevaluation.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(
    private val encryption : Encryption,
    private val userDao : UserDao,
    @IoDispatcher private val ioDispatcher : CoroutineDispatcher,
    private val auth : Authenticator,
)  : ViewModel() {

    private val _isOn = MutableLiveData<ResourceLogin>()
    val isOn : LiveData<ResourceLogin>  get() = _isOn

    fun attemptingLogin(id: String, password: String) = viewModelScope.launch {
        withContext(ioDispatcher){
            _isOn.postValue(ResourceLogin.Loading)
            val users = userDao.getUserById(id).first()

            if (users.isNotEmpty() &&  encryption.encode(password).trim().equals(users[0].password.trim())){
                auth.apply {
                    setIsLogged(true)
                    setUserLogged(users[0].userId)
                }
                Timber.d("User ${users[0].userId}")
                _isOn.postValue(ResourceLogin.Valid)
            }else{
                _isOn.postValue(ResourceLogin.Invalid)
            }
        }
    }

}