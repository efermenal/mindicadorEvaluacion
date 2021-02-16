package com.example.mindicadorevaluation.core.models

import java.io.Serializable

data class Indicator(
    val codigo: String,
    val fecha: String,
    val nombre: String,
    val unidad_medida: String,
    val valor: Double
) : Serializable