package ru.mrfiring.shiftweatherapp.data

import io.reactivex.Single
import okhttp3.ResponseBody
import ru.mrfiring.shiftweatherapp.data.network.City

interface CitiesParser {
    fun parseJson(json: String): Single<List<City>>
    fun decompressGZip(responseBody: ResponseBody): Single<String>
}