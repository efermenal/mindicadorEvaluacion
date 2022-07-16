package com.example.mindicadorevaluation.Repositories

import com.example.mindicadorevaluation.api.MindicadorApi
import com.example.mindicadorevaluation.core.models.Indicator
import com.example.mindicadorevaluation.core.models.IndicatorResponse
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.Resource
import com.example.mindicadorevaluation.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remote: MindicadorApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : RemoteRepository {


    override suspend fun getIndicators(): Resource<List<Indicator>> = withContext(ioDispatcher) {
        val response = remote.getIndicators()
        handleRequest(response)
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
