package ru.mrfiring.shiftweatherapp.di

import androidx.paging.ExperimentalPagingApi
import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.data.CitiesRepositoryImpl
import ru.mrfiring.shiftweatherapp.data.CountriesRepositoryImpl
import ru.mrfiring.shiftweatherapp.data.WeatherRepositoryImpl
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.database.CountriesDao
import ru.mrfiring.shiftweatherapp.data.database.WeatherDao
import ru.mrfiring.shiftweatherapp.data.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.data.paging.CityMediator
import ru.mrfiring.shiftweatherapp.domain.CitiesRepository
import ru.mrfiring.shiftweatherapp.domain.CountriesRepository
import ru.mrfiring.shiftweatherapp.domain.WeatherRepository

@ExperimentalPagingApi
val repositoryModule = module {

    fun provideWeatherRepository(
        weatherDao: WeatherDao,
        citiesDao: CitiesDao,
        networkService: OpenWeatherService,
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherDao, citiesDao, networkService)
    }

    fun provideCitiesRepository(
        citiesDao: CitiesDao,
        cityMediator: CityMediator
    ): CitiesRepository {
        return CitiesRepositoryImpl(citiesDao, cityMediator)
    }

    fun provideCountriesRepository(
        countriesDao: CountriesDao
    ): CountriesRepository = CountriesRepositoryImpl(countriesDao)

    single { provideWeatherRepository(get(), get(), get()) }
    single { provideCitiesRepository(get(), get()) }
    single { provideCountriesRepository(get()) }
}