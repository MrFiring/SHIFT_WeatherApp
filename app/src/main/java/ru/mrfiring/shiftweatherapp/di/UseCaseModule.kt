package ru.mrfiring.shiftweatherapp.di

import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.domain.CitiesRepository
import ru.mrfiring.shiftweatherapp.domain.CountriesRepository
import ru.mrfiring.shiftweatherapp.domain.GetCitiesByCountryFlowUseCase
import ru.mrfiring.shiftweatherapp.domain.GetCitiesFlowUseCase
import ru.mrfiring.shiftweatherapp.domain.GetCountriesUseCase
import ru.mrfiring.shiftweatherapp.domain.GetFavoriteCitiesLiveDataUseCase
import ru.mrfiring.shiftweatherapp.domain.GetWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.WeatherRepository
import ru.mrfiring.shiftweatherapp.domain.location.GetCurrentLocationUseCase

val useCaseModule = module{

    fun provideGetCitiesFlowUseCase(
        repository: CitiesRepository
    ): GetCitiesFlowUseCase {
        return GetCitiesFlowUseCase(repository)
    }

    fun provideGetCitiesByCountryFlowUseCase(
        repository: CitiesRepository
    ): GetCitiesByCountryFlowUseCase {
        return GetCitiesByCountryFlowUseCase(repository)
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
    factory { provideGetCitiesByCountryFlowUseCase(get()) }

    factory { provideGetFavoriteLiveDataCitiesUseCase(get()) }

    factory { provideGetCountriesUseCase(get()) }
    factory { provideUpdateCityUseCase(get()) }

    factory { provideGetWeatherUseCase(get()) }
    factory { provideUpdateWeatherUseCase(get()) }

    factory { GetCurrentLocationUseCase(get()) }
}