package ru.mrfiring.shiftweatherapp.di

import androidx.paging.ExperimentalPagingApi
import com.squareup.moshi.Moshi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.mrfiring.shiftweatherapp.repository.database.WeatherDatabase
import ru.mrfiring.shiftweatherapp.repository.network.BASE_URL
import ru.mrfiring.shiftweatherapp.repository.network.CitiesParser
import ru.mrfiring.shiftweatherapp.repository.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.repository.paging.CityMediator

@ExperimentalPagingApi
val networkModule = module {
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun provideMoshi(): Moshi = Moshi.Builder().build()

    fun provideParser(moshi: Moshi): CitiesParser = CitiesParser(moshi)

    fun provideMediator(weatherService: OpenWeatherService,
                        database: WeatherDatabase,
                        citiesParser: CitiesParser
    ): CityMediator {
        return CityMediator(weatherService, database, citiesParser)
    }

    single {provideRetrofit(BASE_URL)}
    factory {provideMoshi()}
    factory { provideParser(get()) }
    factory { provideMediator(get(), get(), get()) }

}