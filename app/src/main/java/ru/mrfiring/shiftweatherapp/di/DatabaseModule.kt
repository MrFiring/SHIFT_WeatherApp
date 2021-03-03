package ru.mrfiring.shiftweatherapp.di

import android.app.Application
import androidx.room.Room
import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.repository.database.WeatherDatabase

val databaseModule = module {
    fun provideWeatherDatabase(application: Application): WeatherDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            WeatherDatabase::class.java, "weather_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {provideWeatherDatabase(get())}
}