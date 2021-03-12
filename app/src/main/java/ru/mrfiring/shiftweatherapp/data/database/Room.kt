package ru.mrfiring.shiftweatherapp.data.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface CitiesDao {

    @Query("select * from databasecity where country != '' order by country")
    fun getCities(): PagingSource<Int, DatabaseCity>

    @Query("select * from databasecity where favorite = 1")
    fun getFavoriteCities(): LiveData<List<DatabaseCity>>

    @Update
    suspend fun updateCity(city: DatabaseCity)

    @Query("select * from databasecity where id = :id")
    suspend fun getCityById(id: Long): DatabaseCity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(items: List<DatabaseCity>)

    @Query("select count(id) from databasecity")
    suspend fun getCountOfCities(): Int
}

@Dao
interface CountriesDao {
    @Query("select distinct country from databasecity")
    fun getCountriesLiveData(): LiveData<List<String>>
}

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

@Database(
    entities = [DatabaseCity::class,
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