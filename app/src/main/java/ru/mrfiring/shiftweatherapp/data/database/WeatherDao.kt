package ru.mrfiring.shiftweatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseMainWeatherParameters
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseRain
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseSnow
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWeather
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWeatherContainer
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseWind

@Dao
interface WeatherDao {
    @Query("select * from databaseweathercontainer where id = :id")
    suspend fun getWeatherContainerById(id: Long): DatabaseWeatherContainer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherContainer(item: DatabaseWeatherContainer)

    @Query("select * from databaseweather where id = :id")
    suspend fun getWeatherListById(id: Long): List<DatabaseWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWeather(items: List<DatabaseWeather>)

    @Query("select * from databasemainweatherparameters where id = :id")
    suspend fun getMainWeatherParametersById(id: Long): DatabaseMainWeatherParameters

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainWeatherParameters(item: DatabaseMainWeatherParameters)

    @Query("select * from databasewind where id = :id")
    suspend fun getWindById(id: Long): DatabaseWind

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWind(item: DatabaseWind)

    @Query("select * from databaserain where id = :id")
    suspend fun getRainById(id: Long): DatabaseRain

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRain(item: DatabaseRain)

    @Query("select * from databasesnow where id = :id")
    suspend fun getSnowById(id: Long): DatabaseSnow

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSnow(item: DatabaseSnow)
}