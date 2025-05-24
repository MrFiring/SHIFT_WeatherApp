package ru.mrfiring.shiftweatherapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseCity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val state: String,
    val country: String,
    val longitude: Double,
    val latitude: Double,
    val favorite: Boolean = false
)

