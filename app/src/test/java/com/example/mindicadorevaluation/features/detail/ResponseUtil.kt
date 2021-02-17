package com.example.mindicadorevaluation.features.detail

import com.example.mindicadorevaluation.core.models.*

class ResponseUtil {

fun  getExampleIndicatorResponse() : IndicatorResponse{

    return IndicatorResponse(
        autor = "",
        version = "1",

        fecha = "01/01/2022",
        dolar = Dolar( "","","","",0.0),
        dolar_intercambio = DolarIntercambio( "","","","",0.0),
        euro = Euro( "","","","",0.0),
        imacec = Imacec( "","","","",0.0),
        ipc = Ipc( "","","","",0.0),
        ivp = Ivp( "","","","",0.0),
        bitcoin = Bitcoin( "","","","",0.0),
        libra_cobre = LibraCobre( "","","","",0.0),
        tasa_desempleo = TasaDesempleo( "","","","",0.0),
        tpm = Tpm( "","","","",0.0),
        uf = Uf( "","","","",0.0),
        utm = Utm( "","","","",0)
    )
}

}