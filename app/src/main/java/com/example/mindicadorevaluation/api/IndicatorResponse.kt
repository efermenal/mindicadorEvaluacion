package com.example.mindicadorevaluation.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class IndicatorResponse(
    @Json(name = "codigo") val code: String,
    @Json(name = "fecha") val updatedDate: String,
    @Json(name = "nombre") val name: String,
    @Json(name = "unidad_medida") val measurementUnit: String,
    @Json(name = "valor") val value: Double,
) : Serializable
