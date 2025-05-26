package ru.mrfiring.shiftweatherapp.data.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import ru.mrfiring.shiftweatherapp.data.network.model.WeatherContainer

private const val API_KEY = "2e94be02060a9fb7a13cfd7e5027ca72"
const val BASE_URL= "https://api.openweathermap.org/data/2.5/"
private const val FILE_NAME = "city.list.min.json.gz"
private const val FILE_URL = "https://bulk.openweathermap.org/sample/${FILE_NAME}"

interface OpenWeatherService{
    @GET
    suspend fun getCitiesFile(@Url path: String = FILE_URL): ResponseBody?

    @GET("weather")
    suspend fun getWeatherContainerByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "us",
        @Query("appid") apiKey: String = API_KEY
    ): WeatherContainer
}
