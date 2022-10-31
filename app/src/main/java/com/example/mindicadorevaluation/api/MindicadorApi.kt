package com.example.mindicadorevaluation.api

import com.example.mindicadorevaluation.core.models.IndicatorResponse
import retrofit2.Response
import retrofit2.http.GET

interface MindicadorApi {

    @GET("api")
    suspend fun getIndicators(): Response<IndicatorResponse>
}
