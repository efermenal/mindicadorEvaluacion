package com.example.mindicadorevaluation.features.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindicadorevaluation.core.models.Indicator
import com.example.mindicadorevaluation.core.models.IndicatorResponse
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.Resource
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DetailViewModel
    @Inject
    constructor (private val remote : RemoteRepository) : ViewModel() {

    private val _responseApi = MutableLiveData<Resource<IndicatorResponse>>()
    val responseApi : LiveData<Resource<IndicatorResponse>>
        get() = _responseApi

    fun getIndicators() = viewModelScope.launch {
      _responseApi.postValue(Resource.Loading())
       val response = remote.getIndicators()

        if (response is Resource.Success){
            _responseApi.postValue(response)
        }

    }


}