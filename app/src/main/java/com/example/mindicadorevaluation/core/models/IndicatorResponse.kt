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
        //TODO refactor this code; thinking about a better approach
        val indicatorList = mutableListOf<Indicator>()
        indicatorList.add(Indicator(bitcoin.codigo, bitcoin.fecha, bitcoin.nombre, bitcoin.unidad_medida, bitcoin.valor))
        indicatorList.add(Indicator(dolar.codigo, dolar.fecha, dolar.nombre, dolar.unidad_medida, dolar.valor))
        indicatorList.add(Indicator(euro.codigo, euro.fecha, euro.nombre, euro.unidad_medida, euro.valor))
        indicatorList.add(Indicator(dolar_intercambio.codigo, dolar_intercambio.fecha, dolar_intercambio.nombre, dolar_intercambio.unidad_medida, dolar_intercambio.valor))
        indicatorList.add(Indicator(uf.codigo, uf.fecha, uf.nombre, uf.unidad_medida, uf.valor))
        indicatorList.add(Indicator(ivp.codigo, ivp.fecha, ivp.nombre, ivp.unidad_medida, ivp.valor))
        indicatorList.add(Indicator(utm.codigo, utm.fecha, utm.nombre, utm.unidad_medida, utm.valor.toDouble()))
        indicatorList.add(Indicator(libra_cobre.codigo, libra_cobre.fecha, libra_cobre.nombre, libra_cobre.unidad_medida, libra_cobre.valor))
        indicatorList.add(Indicator(tasa_desempleo.codigo, tasa_desempleo.fecha, tasa_desempleo.nombre, tasa_desempleo.unidad_medida, tasa_desempleo.valor))
        indicatorList.add(Indicator(imacec.codigo, imacec.fecha, imacec.nombre, imacec.unidad_medida, imacec.valor))
        return indicatorList
    }
}