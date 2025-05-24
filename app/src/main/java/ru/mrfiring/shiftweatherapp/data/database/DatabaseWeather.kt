package ru.mrfiring.shiftweatherapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseWeather(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val weatherId: Int,
    val main: String,
    val description: String,
    val icon: String
)