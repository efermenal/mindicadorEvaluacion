package com.example.mindicadorevaluation.repositories

import com.example.mindicadorevaluation.api.MindicadorApi
import com.example.mindicadorevaluation.core.models.Indicator
import com.example.mindicadorevaluation.core.models.IndicatorResponse
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.DispatcherProvider
import com.example.mindicadorevaluation.core.utils.Resource
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remote: MindicadorApi,
    private val dispatcherProvider: DispatcherProvider,
) : RemoteRepository {

    override suspend fun getIndicators(): Resource<List<Indicator>> =
        withContext(dispatcherProvider.io()) {
            try {
                val response = remote.getIndicators()
                handleRequest(response)
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        }

    private fun handleRequest(response: Response<IndicatorResponse>): Resource<List<Indicator>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result.getListIndicator())
            }
        }
        return Resource.Error(response.message())
    }

}
