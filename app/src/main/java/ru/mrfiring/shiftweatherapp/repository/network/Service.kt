package ru.mrfiring.shiftweatherapp.repository.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

private const val API_KEY = "2e94be02060a9fb7a13cfd7e5027ca72"
private const val BASE_URL= "https://api.openweathermap.org/data/2.5/"
private const val FILE_NAME = "city.list.min.json.gz"
private const val FILE_URL = "https://bulk.openweathermap.org/sample/${FILE_NAME}"

//Used in BindingAdapter.kt by Glide to load Weather icon
const val IMG_URL = "http://openweathermap.org/img/wn/%s@2x.png"
//Used in BindingAdapter.kt by Glide to load country flag
const val FLAG_URL = "https://www.countryflags.io/%s/flat/64.png"

interface OpenWeatherService{
    @GET
    suspend fun getCitiesFile(@Url path: String = FILE_URL): ResponseBody?

    @GET("weather")
    suspend fun getWeatherContainerByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") apiKey: String = API_KEY
    ): WeatherContainer
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

