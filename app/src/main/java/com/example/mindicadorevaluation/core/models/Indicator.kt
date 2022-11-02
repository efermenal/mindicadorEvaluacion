package com.example.mindicadorevaluation.core.models

import java.io.Serializable

data class Indicator(
    val code: String,
    val updatedDate: String,
    val name: String,
    val measurementUnit: String,
    val value: Double,
) : Serializable
