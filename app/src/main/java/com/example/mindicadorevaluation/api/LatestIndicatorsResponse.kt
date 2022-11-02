package com.example.mindicadorevaluation.api

import com.example.mindicadorevaluation.core.models.Indicator
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LatestIndicatorsResponse(
    @Json(name = "autor") val autor: String,
    @Json(name = "fecha") val fecha: String,
    @Json(name = "version") val version: String,
    @Json(name = "bitcoin") val bitcoin: IndicatorResponse,
    @Json(name = "dolar") val dolar: IndicatorResponse,
    @Json(name = "dolar_intercambio") val dolar_intercambio: IndicatorResponse,
    @Json(name = "euro") val euro: IndicatorResponse,
    @Json(name = "imacec") val imacec: IndicatorResponse,
    @Json(name = "ipc") val ipc: IndicatorResponse,
    @Json(name = "ivp") val ivp: IndicatorResponse,
    @Json(name = "libra_cobre") val libra_cobre: IndicatorResponse,
    @Json(name = "tasa_desempleo") val tasa_desempleo: IndicatorResponse,
    @Json(name = "tpm") val tpm: IndicatorResponse,
    @Json(name = "uf") val uf: IndicatorResponse,
    @Json(name = "utm") val utm: IndicatorResponse,
)

fun LatestIndicatorsResponse.toDomain(): List<Indicator> {
    return mutableListOf(
        bitcoin.toDomain(),
        dolar.toDomain(),
        dolar_intercambio.toDomain(),
        euro.toDomain(),
        imacec.toDomain(),
        ipc.toDomain(),
        ivp.toDomain(),
        libra_cobre.toDomain(),
        tasa_desempleo.toDomain(),
        tpm.toDomain(),
        uf.toDomain(),
        utm.toDomain(),
    )
}

fun IndicatorResponse.toDomain(): Indicator {
    return Indicator(
        code = code,
        updatedDate = updatedDate,
        name = name,
        measurementUnit = name,
        value = value,
    )
}
