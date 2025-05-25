package ru.mrfiring.shiftweatherapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseWind(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val speed: Double,
    val deg: Double,
    val gust: Double
)