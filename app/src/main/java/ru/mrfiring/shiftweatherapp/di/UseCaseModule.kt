package ru.mrfiring.shiftweatherapp.di

import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.domain.*

val useCaseModule = module{

    fun provideGetCitiesFlowUseCase(
        repository: CitiesRepository
    ): GetCitiesFlowUseCase {
        return GetCitiesFlowUseCase(repository)
    }

    fun provideGetFavoriteCitiesUseCase(
        repository: CitiesRepository
    ): GetFavoriteCitiesUseCase {
        return GetFavoriteCitiesUseCase(repository)
    }

    fun provideUpdateCityUseCase(
        repository: CitiesRepository
    ): UpdateCityUseCase {
        return UpdateCityUseCase(repository)
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

    factory { provideGetCitiesFlowUseCase(get()) }
    factory { provideGetFavoriteCitiesUseCase(get()) }
    factory { provideUpdateCityUseCase(get()) }

    factory { provideGetWeatherUseCase(get()) }
    factory { provideUpdateWeatherUseCase(get()) }
}