package com.example.mindicadorevaluation.core.services

import com.example.mindicadorevaluation.core.models.IndicatorResponse
import com.example.mindicadorevaluation.core.utils.Resource

interface RemoteRepository {
    suspend fun getIndicators() : Resource<IndicatorResponse>
}