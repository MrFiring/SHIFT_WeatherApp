package ru.mrfiring.shiftweatherapp.data

import okhttp3.ResponseBody
import ru.mrfiring.shiftweatherapp.data.network.model.CityModel

interface CitiesParser {
    suspend fun parseJson(json: String): List<CityModel>
    suspend fun decompressGZip(responseBody: ResponseBody?): String
}