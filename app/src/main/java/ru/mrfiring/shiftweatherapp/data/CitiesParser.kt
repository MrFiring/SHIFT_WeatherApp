package ru.mrfiring.shiftweatherapp.data

import okhttp3.ResponseBody
import ru.mrfiring.shiftweatherapp.data.network.City

interface CitiesParser {
    suspend fun parseJson(json: String) : List<City>
    suspend fun decompressGZip(responseBody: ResponseBody?): String
}