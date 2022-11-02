package com.example.mindicadorevaluation.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader

class LatestIndicatorsJsonAdapter {

    private var author = ""
    private var dated = ""
    private var version = ""
    private var code: String? = null
    private var updatedDate: String? = null
    private var name: String? = null
    private var measurementUnit: String? = null
    private var value: Double? = null
    private var bitcoin: IndicatorResponse? = null
    private var dolar: IndicatorResponse? = null
    private var dolar_intercambio: IndicatorResponse? = null
    private var euro: IndicatorResponse? = null
    private var imacec: IndicatorResponse? = null
    private var ipc: IndicatorResponse? = null
    private var ivp: IndicatorResponse? = null
    private var libra_cobre: IndicatorResponse? = null
    private var tasa_desempleo: IndicatorResponse? = null
    private var tpm: IndicatorResponse? = null
    private var uf: IndicatorResponse? = null
    private var utm: IndicatorResponse? = null

    companion object {
        val TOP_LEVEL_NAMES = JsonReader.Options.of(
            "autor",
            "fecha",
            "version",
            "bitcoin",
            "dolar",
            "dolar_intercambio",
            "euro",
            "imacec",
            "ipc",
            "ivp",
            "libra_cobre",
            "tasa_desempleo",
            "tpm",
            "uf",
            "utm"
        )
        val INDICATOR_NAMES =
            JsonReader.Options.of("codigo", "fecha", "nombre", "unidad_medida", "valor")
    }

    @FromJson
    fun fromJson(reader: JsonReader): LatestIndicatorsResponse {

        with(reader) {
            readJson {
                when (selectName(TOP_LEVEL_NAMES)) {
                    0 -> author = nextString()
                    1 -> dated = nextString()
                    2 -> version = nextString()
                    3 -> {
                        readIndicator()
                        bitcoin = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    4 -> {
                        readIndicator()
                        dolar = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    5 -> {
                        readIndicator()
                        dolar_intercambio = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    6 -> {
                        readIndicator()
                        euro = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    7 -> {
                        readIndicator()
                        imacec = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    8 -> {
                        readIndicator()
                        ipc = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    9 -> {
                        readIndicator()
                        ivp = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    10 -> {
                        readIndicator()
                        libra_cobre = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    11 -> {
                        readIndicator()
                        tasa_desempleo = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    12 -> {
                        readIndicator()
                        tpm = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    13 -> {
                        readIndicator()
                        uf = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    14 -> {
                        readIndicator()
                        utm = IndicatorResponse(
                            code!!,
                            updatedDate!!,
                            name!!,
                            measurementUnit!!,
                            value!!
                        )
                    }
                    else -> {
                        skipName()
                        skipValue()
                    }
                }
            }
        }

        checkNulls()

        return LatestIndicatorsResponse(
            author,
            dated,
            version,
            bitcoin = bitcoin!!,
            dolar = dolar!!,
            dolar_intercambio = dolar_intercambio!!,
            euro = euro!!,
            imacec = imacec!!,
            ipc = ipc!!,
            ivp = ivp!!,
            libra_cobre = libra_cobre!!,
            tasa_desempleo = tasa_desempleo!!,
            tpm = tpm!!,
            uf = uf!!,
            utm = utm!!,
        )

    }

    private fun checkNulls() {
        notNull(bitcoin) {
            throw JsonDataException("Missing required field: bitcoin")
        }
        notNull(dolar) {
            throw JsonDataException("Missing required field: dolar")
        }
        notNull(dolar_intercambio) {
            throw JsonDataException("Missing required field: dolar_intercambio")
        }
        notNull(euro) {
            throw JsonDataException("Missing required field: euro")
        }
        notNull(imacec) {
            throw JsonDataException("Missing required field: imacec")
        }
        notNull(ipc) {
            throw JsonDataException("Missing required field: ipc")
        }
        notNull(ivp) {
            throw JsonDataException("Missing required field: ivp")
        }
        notNull(libra_cobre) {
            throw JsonDataException("Missing required field: libra_cobre")
        }
        notNull(tasa_desempleo) {
            throw JsonDataException("Missing required field: tasa_desempleo")
        }
        notNull(tpm) {
            throw JsonDataException("Missing required field: tpm")
        }
        notNull(uf) {
            throw JsonDataException("Missing required field: uf")
        }
        notNull(utm) {
            throw JsonDataException("Missing required field: utm")
        }
    }

    private fun notNull(entity: Any?, message: () -> Unit) {
        if (entity == null) {
            message()
        }
    }

    private fun JsonReader.readIndicator() {
        readJson {
            when (selectName(INDICATOR_NAMES)) {
                0 -> code = nextString()
                1 -> updatedDate = nextString()
                2 -> name = nextString()
                3 -> measurementUnit = nextString()
                4 -> value = nextDouble()
                else -> {
                    skipName()
                    skipValue()
                }
            }
        }

    }

}

fun JsonReader.readJson(block: () -> Unit) {
    beginObject()
    while (hasNext()) {
        block()
    }
    endObject()
}
