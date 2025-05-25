package ru.mrfiring.shiftweatherapp.data.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseCity

@Dao
interface CitiesDao {

    @Query("select * from databasecity where country != '' order by country")
    fun getCities(): PagingSource<Int, DatabaseCity>

    @Query("select * from databasecity where country = :country")
    fun getCitiesByCountry(country: String): PagingSource<Int, DatabaseCity>

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