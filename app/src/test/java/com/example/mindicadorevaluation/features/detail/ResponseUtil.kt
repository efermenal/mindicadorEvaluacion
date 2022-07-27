package com.example.mindicadorevaluation.features.detail

import com.example.mindicadorevaluation.core.models.*

object ResponseUtil {

    val indicatorResponseExample = IndicatorResponse(
        autor = "",
        version = "1",
        fecha = "01/01/2022",
        dolar = Dolar("dollar", "", "", "", 0.0),
        dolar_intercambio = DolarIntercambio("", "", "", "", 0.0),
        euro = Euro("", "", "", "", 0.0),
        imacec = Imacec("", "", "", "", 0.0),
        ipc = Ipc("", "", "", "", 0.0),
        ivp = Ivp("", "", "", "", 0.0),
        bitcoin = Bitcoin("bitcoin", "", "", "", 0.0),
        libra_cobre = LibraCobre("", "", "", "", 0.0),
        tasa_desempleo = TasaDesempleo("", "", "", "", 0.0),
        tpm = Tpm("", "", "", "", 0.0),
        uf = Uf("", "", "", "", 0.0),
        utm = Utm("", "", "", "", 0)
    )

    private val indicator = Indicator(
        codigo = "dollar",
        fecha = "01/01/2022",
        nombre = "dollar",
        unidad_medida = "um",
        valor = 0.0
    )

    val indicatorList = listOf(
        indicator,
        indicator.copy(codigo = "bitcoin", nombre = "bitcoin"),
        indicator.copy(codigo = "euro", nombre = "euro"),
        indicator.copy(codigo = "uf", nombre = "uf"),
        indicator.copy(codigo = "utm", nombre = "utm"),
    )


}
