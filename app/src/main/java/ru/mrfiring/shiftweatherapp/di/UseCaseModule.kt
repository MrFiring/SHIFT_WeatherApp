package ru.mrfiring.shiftweatherapp.di

import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.domain.*

val useCaseModule = module{

    fun provideGetCitiesLiveDataUseCase(
        repository: CitiesRepository
    ): GetCitiesLiveDataUseCase{
        return GetCitiesLiveDataUseCase(repository)
    }

    fun provideGetWeatherUseCase(
        repository: WeatherRepository
    ): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    fun provideUpdateWeatherUseCase(
        repository: WeatherRepository
    ): UpdateWeatherUseCase {
        return UpdateWeatherUseCase(repository)
    }

    factory { provideGetCitiesLiveDataUseCase(get()) }
    factory { provideGetWeatherUseCase(get()) }
    factory { provideUpdateWeatherUseCase(get()) }
}