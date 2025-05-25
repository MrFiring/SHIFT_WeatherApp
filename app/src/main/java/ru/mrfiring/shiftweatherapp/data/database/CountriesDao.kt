package ru.mrfiring.shiftweatherapp.data.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CountriesDao {
    @Query("select distinct country from databasecity")
    suspend fun getCountries(): List<String>
}