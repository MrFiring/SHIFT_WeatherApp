package ru.mrfiring.shiftweatherapp.repository.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mrfiring.shiftweatherapp.repository.network.City

@Dao
interface CitiesDao{

    @Query("select * from databasecity order by country")
    fun getCities(): LiveData<List<DatabaseCity>>

    @Query("select * from databasecity where id = :id")
    suspend fun getCityById(id: Long): DatabaseCity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(items:  List<DatabaseCity>)

}

@Dao
interface WeatherDao{
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

@Database(
    entities = [DatabaseCity::class,
        DatabaseMainWeatherParameters::class,
        DatabaseWeatherContainer::class,
        DatabaseWeather::class,
        DatabaseWind::class,
        DatabaseRain::class,
        DatabaseSnow::class
    ],
    version = 1
)
abstract class WeatherDatabase: RoomDatabase(){
    abstract val citiesDao: CitiesDao
    abstract val weatherDao: WeatherDao
}

private lateinit var INSTANCE: WeatherDatabase
fun getDatabase(context: Context): WeatherDatabase{
    synchronized(WeatherDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java, "weather_db"
                )
                .build()
        }
    }
    return INSTANCE
}