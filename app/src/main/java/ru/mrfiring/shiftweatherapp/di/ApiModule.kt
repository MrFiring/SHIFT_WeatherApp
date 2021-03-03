package ru.mrfiring.shiftweatherapp.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.mrfiring.shiftweatherapp.repository.network.OpenWeatherService

val apiModule = module{

    fun provideOpenWeatherService(retrofit: Retrofit): OpenWeatherService {
        return retrofit.create(OpenWeatherService::class.java)
    }

    single { provideOpenWeatherService(get())}
}