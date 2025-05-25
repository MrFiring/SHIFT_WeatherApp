package ru.mrfiring.shiftweatherapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseSnow(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val forLastOneHour: Double,
    val forLastThreeHours: Double
)