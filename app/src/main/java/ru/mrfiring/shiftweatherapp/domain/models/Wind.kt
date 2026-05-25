package ru.mrfiring.shiftweatherapp.domain.models

import kotlin.math.roundToInt

data class Wind(
    val speed: Double,
    val deg: Double,
    val gust: Double
) {
    fun degToWord(): String {
        when (deg.roundToInt()) {
            0 -> {
                return "N"
            }

            90 -> {
                return "E"
            }

            180 -> {
                return "S"
            }

            270 -> {
                return "W"
            }

            in 1..89 -> {
                return "NE"
            }

            in 91..179 -> {
                return "SE"
            }

            in 181..269 -> {
                return "SW"
            }

            in 271..359 -> {
                return "NW"
            }

            else -> {
                return "NA"
            }
        }

    }
}