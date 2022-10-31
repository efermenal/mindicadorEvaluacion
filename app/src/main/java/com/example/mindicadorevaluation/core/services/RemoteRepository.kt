package com.example.mindicadorevaluation.core.services

import com.example.mindicadorevaluation.core.models.Indicator
import com.example.mindicadorevaluation.core.utils.Resource

interface RemoteRepository {
    suspend fun getIndicators(): Resource<List<Indicator>>
}
