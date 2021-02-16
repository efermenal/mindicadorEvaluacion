package com.example.mindicadorevaluation.core.models

import timber.log.Timber

data class IndicatorResponse(
    val autor: String,
    val bitcoin: Bitcoin,
    val dolar: Dolar,
    val dolar_intercambio: DolarIntercambio,
    val euro: Euro,
    val fecha: String,
    val imacec: Imacec,
    val ipc: Ipc,
    val ivp: Ivp,
    val libra_cobre: LibraCobre,
    val tasa_desempleo: TasaDesempleo,
    val tpm: Tpm,
    val uf: Uf,
    val utm: Utm,
    val version: String
){

    fun getListIndicator () : MutableList<Indicator>{
        val indicatorList = mutableListOf<Indicator>()
        indicatorList.add(Indicator(bitcoin.codigo, bitcoin.fecha, bitcoin.nombre, bitcoin.unidad_medida, bitcoin.valor))
        indicatorList.add(Indicator(dolar.codigo, dolar.fecha, dolar.nombre, dolar.unidad_medida, dolar.valor))
        indicatorList.add(Indicator(euro.codigo, euro.fecha, euro.nombre, euro.unidad_medida, euro.valor))
        indicatorList.add(Indicator(dolar_intercambio.codigo, dolar_intercambio.fecha, dolar_intercambio.nombre, dolar_intercambio.unidad_medida, dolar_intercambio.valor))
        return indicatorList
    }
}