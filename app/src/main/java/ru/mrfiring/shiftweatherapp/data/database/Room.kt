package ru.mrfiring.shiftweatherapp.data.database

import androidx.paging.PagingSource
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface CitiesDao{

    @Query("select * from databasecity where country != '' order by country")
    fun getCities(): PagingSource<Int, DatabaseCity>

    @Query("select * from databasecity where id = :id")
    fun getCityById(id: Long): Maybe<DatabaseCity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(items: List<DatabaseCity>): Completable

    @Query("select count(id) from databasecity")
    fun getCountOfCities(): Int
}

@Dao
interface WeatherDao{
    @Query("select * from databaseweathercontainer where id = :id")
    fun getWeatherContainerById(id: Long): Maybe<DatabaseWeatherContainer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherContainer(item: DatabaseWeatherContainer): Completable

    @Query("select * from databaseweather where id = :id")
    fun getWeatherListById(id: Long): Maybe<List<DatabaseWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWeather(items: List<DatabaseWeather>): Completable

    @Query("select * from databasemainweatherparameters where id = :id")
    fun getMainWeatherParametersById(id: Long): Maybe<DatabaseMainWeatherParameters>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMainWeatherParameters(item: DatabaseMainWeatherParameters): Completable

    @Query("select * from databasewind where id = :id")
    fun getWindById(id: Long): Maybe<DatabaseWind>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWind(item: DatabaseWind): Completable

    @Query("select * from databaserain where id = :id")
    fun getRainById(id: Long): Maybe<DatabaseRain>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRain(item: DatabaseRain): Completable

    @Query("select * from databasesnow where id = :id")
    fun getSnowById(id: Long): Maybe<DatabaseSnow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSnow(item: DatabaseSnow): Completable
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
    version = 4
)
abstract class WeatherDatabase: RoomDatabase(){
    abstract val citiesDao: CitiesDao
    abstract val weatherDao: WeatherDao
}