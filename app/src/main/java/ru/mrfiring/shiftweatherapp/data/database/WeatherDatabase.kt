package ru.mrfiring.shiftweatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseCity
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseMainWeatherParameters
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseRain
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseSnow
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWeather
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWeatherContainer
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWind

@Database(
    entities = [
        DatabaseCity::class,
        DatabaseMainWeatherParameters::class,
        DatabaseWeatherContainer::class,
        DatabaseWeather::class,
        DatabaseWind::class,
        DatabaseRain::class,
        DatabaseSnow::class,
    ],
    version = 5
)
abstract class WeatherDatabase: RoomDatabase(){
    abstract val citiesDao: CitiesDao
    abstract val weatherDao: WeatherDao
    abstract val countriesDao: CountriesDao
}