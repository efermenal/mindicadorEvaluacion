package com.example.mindicadorevaluation.Repositories

import com.example.mindicadorevaluation.api.MindicadorApi
import com.example.mindicadorevaluation.core.models.IndicatorResponse
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.Resource
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteRepositoryImpl
 @Inject constructor(
     private  val remote: MindicadorApi,
     ) : RemoteRepository {


    override suspend fun getIndicators(): Resource<IndicatorResponse> {
        val response = remote.getIndicators()
        return handleRequest(response)
    }

    private fun handleRequest(response: Response<IndicatorResponse>) : Resource<IndicatorResponse>{
        if (response.isSuccessful){
            response.body()?.let { result ->
            return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}