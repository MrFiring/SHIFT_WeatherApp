package ru.mrfiring.shiftweatherapp.di

import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.domain.CitiesRepository
import ru.mrfiring.shiftweatherapp.domain.CountriesRepository
import ru.mrfiring.shiftweatherapp.domain.GetCitiesByCountryFlowUseCase
import ru.mrfiring.shiftweatherapp.domain.GetCitiesFlowUseCase
import ru.mrfiring.shiftweatherapp.domain.GetCountriesUseCase
import ru.mrfiring.shiftweatherapp.domain.GetFavoriteCitiesUseCase
import ru.mrfiring.shiftweatherapp.domain.GetWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.WeatherRepository
import ru.mrfiring.shiftweatherapp.domain.location.GetCurrentLocationUseCase
import ru.mrfiring.shiftweatherapp.domain.onboarding.IsOnboardingPassedUseCase
import ru.mrfiring.shiftweatherapp.domain.onboarding.SetOnboardingPassedUseCase
import ru.mrfiring.shiftweatherapp.domain.theme.GetDarkThemeUseCase
import ru.mrfiring.shiftweatherapp.domain.theme.SetDarkThemeUseCase

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

    factory { provideGetFavoriteCitiesUseCase(get()) }

    factory { provideGetCountriesUseCase(get()) }
    factory { provideUpdateCityUseCase(get()) }

    factory { provideGetWeatherUseCase(get()) }
    factory { provideUpdateWeatherUseCase(get()) }

    factory { GetCurrentLocationUseCase(get()) }

    factory { IsOnboardingPassedUseCase(repository = get()) }
    factory { SetOnboardingPassedUseCase(repository = get()) }

    factory { GetDarkThemeUseCase(repository = get()) }
    factory { SetDarkThemeUseCase(repository = get()) }
}