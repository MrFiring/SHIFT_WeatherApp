package ru.mrfiring.shiftweatherapp.di

import android.app.Application
import androidx.room.Room
import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.data.database.*

val databaseModule = module {
    fun provideWeatherDatabase(application: Application): WeatherDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            WeatherDatabase::class.java, "weather_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCitiesDao(weatherDatabase: WeatherDatabase): CitiesDao{
        return CitiesDao_Impl(weatherDatabase)
    }

    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao{
        return WeatherDao_Impl(weatherDatabase)
    }



    single {provideWeatherDatabase(get())}
    single {provideCitiesDao(get())}
    single {provideWeatherDao(get())}
}