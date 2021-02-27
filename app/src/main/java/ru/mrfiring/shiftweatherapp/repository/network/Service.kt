package ru.mrfiring.shiftweatherapp.repository.network

import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

private const val API_KEY = "2e94be02060a9fb7a13cfd7e5027ca72"
private const val BASE_URL= "https://api.openweathermap.org/data/2.5/"
private const val FILE_NAME = "city.list.min.json.gz"
private const val FILE_URL = "https://bulk.openweathermap.org/sample/${FILE_NAME}"

interface OpenWeatherService{
    @GET
    suspend fun getCitiesFile(@Url path: String = FILE_URL): ResponseBody
}

object OpenWeatherApi{
    val moshi: Moshi = Moshi.Builder()
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val openWeatherService: OpenWeatherService = retrofit.create(OpenWeatherService::class.java)
}

