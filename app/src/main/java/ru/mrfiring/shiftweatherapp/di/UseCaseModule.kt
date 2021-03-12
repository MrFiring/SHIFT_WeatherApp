package ru.mrfiring.shiftweatherapp.di

import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.domain.*

val useCaseModule = module{

    fun provideGetCitiesFlowUseCase(
        repository: CitiesRepository
    ): GetCitiesFlowUseCase {
        return GetCitiesFlowUseCase(repository)
    }

    fun provideGetFavoriteLiveDataCitiesUseCase(
        repository: CitiesRepository
    ): GetFavoriteCitiesLiveDataUseCase {
        return GetFavoriteCitiesLiveDataUseCase(repository)
    }

    fun provideUpdateCityUseCase(
        repository: CitiesRepository
    ): UpdateCityUseCase {
        return UpdateCityUseCase(repository)
    }

    fun provideGetCountriesUseCase(
        repository: CountriesRepository
    ): GetCountriesUseCase {
        return GetCountriesUseCase(repository)
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
    factory { provideGetFavoriteLiveDataCitiesUseCase(get()) }
    factory { provideGetCountriesUseCase(get()) }
    factory { provideUpdateCityUseCase(get()) }

    factory { provideGetWeatherUseCase(get()) }
    factory { provideUpdateWeatherUseCase(get()) }
}