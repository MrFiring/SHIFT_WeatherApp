package ru.mrfiring.shiftweatherapp.di

import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.domain.*

val useCaseModule = module{

    fun provideGetCitiesFlowableUseCase(
        repository: CitiesRepository
    ): GetCitiesFlowableUseCase {
        return GetCitiesFlowableUseCase(repository)
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

    factory { provideGetCitiesFlowableUseCase(get()) }
    factory { provideGetWeatherUseCase(get()) }
    factory { provideUpdateWeatherUseCase(get()) }
}