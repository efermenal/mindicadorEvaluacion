package com.example.mindicadorevaluation.features.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindicadorevaluation.core.models.Indicator
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.core.services.NetworkInformation
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.Resource
import com.example.mindicadorevaluation.features.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListIndicatorViewModel @Inject constructor(
    private val remote: RemoteRepository,
    private val netInfo: NetworkInformation,
    private val auth: Authenticator,
) : ViewModel() {

    data class ViewState(
        val isLoading: Boolean = false,
        val indicators: List<Indicator> = emptyList(),
    )

    sealed class Command {
        object NoInternet : Command()
        object Error : Command()
        object LogOut : Command()
    }

    private val _viewState = MutableLiveData(ViewState())
    val viewState: LiveData<ViewState>
        get() = _viewState
    val command: SingleLiveEvent<Command> = SingleLiveEvent()

    private val currentViewState: ViewState get() = viewState.value!!

    init {
        getIndicators()
    }

    fun getUserName() = auth.getUserLogged()

    fun getIndicators() = viewModelScope.launch {

        if (netInfo.isOnline()) {
            _viewState.postValue(currentViewState.copy(isLoading = true))
            try {
                val response = remote.getIndicators()

                if (response is Resource.Success) {
                    _viewState.postValue(
                        currentViewState.copy(
                            isLoading = false,
                            indicators = response.data ?: emptyList()
                        )
                    )
                }
            } catch (e: Exception) {
                _viewState.postValue(currentViewState.copy(isLoading = false))
                command.postValue(Command.Error)
            }
        } else {
            _viewState.postValue(currentViewState.copy(isLoading = false))
            command.postValue(Command.NoInternet)
        }
    }

    fun logout() {
        auth.setIsLogged(false)
        command.value = Command.LogOut
    }
}
