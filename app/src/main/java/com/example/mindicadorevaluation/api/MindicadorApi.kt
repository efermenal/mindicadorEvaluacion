package com.example.mindicadorevaluation.api

import retrofit2.Response
import retrofit2.http.GET

interface MindicadorApi {

    @GET("api")
    suspend fun getIndicators(): Response<LatestIndicatorsResponse>
}
