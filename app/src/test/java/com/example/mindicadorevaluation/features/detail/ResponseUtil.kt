package com.example.mindicadorevaluation.features.detail

import com.example.mindicadorevaluation.api.IndicatorResponse
import com.example.mindicadorevaluation.api.LatestIndicatorsResponse
import com.example.mindicadorevaluation.core.models.Indicator

object ResponseUtil {

    val latestIndicatorsResponseExample = LatestIndicatorsResponse(
        autor = "",
        version = "1",
        fecha = "01/01/2022",
        dolar = IndicatorResponse("dollar", "", "", "", 0.0),
        dolar_intercambio = IndicatorResponse("", "", "", "", 0.0),
        euro = IndicatorResponse("", "", "", "", 0.0),
        imacec = IndicatorResponse("", "", "", "", 0.0),
        ipc = IndicatorResponse("", "", "", "", 0.0),
        ivp = IndicatorResponse("", "", "", "", 0.0),
        bitcoin = IndicatorResponse("bitcoin", "", "", "", 0.0),
        libra_cobre = IndicatorResponse("", "", "", "", 0.0),
        tasa_desempleo = IndicatorResponse("", "", "", "", 0.0),
        tpm = IndicatorResponse("", "", "", "", 0.0),
        uf = IndicatorResponse("", "", "", "", 0.0),
        utm = IndicatorResponse("", "", "", "", 0.0)
    )

    private val indicator = Indicator(
        code = "dollar",
        updatedDate = "01/01/2022",
        name = "dollar",
        measurementUnit = "um",
        value = 0.0,
    )

    val indicatorList = listOf(
        indicator,
        indicator.copy(code = "bitcoin", name = "bitcoin"),
        indicator.copy(code = "euro", name = "euro"),
        indicator.copy(code = "uf", name = "uf"),
        indicator.copy(code = "utm", name = "utm"),
    )


}
