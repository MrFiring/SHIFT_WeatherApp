package ru.mrfiring.shiftweatherapp.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.mrfiring.shiftweatherapp.data.database.*
import java.io.IOException

class WeatherDaoTest {
    private lateinit var weatherDao: WeatherDao
    private lateinit var database: WeatherDatabase

    private val container = DatabaseWeatherContainer(
        0, "Moscow", "hello", 155799, 4488889, 90.0,
        weather = DatabaseWeather(
            0, 10, "Clear", "Clear", "10d"
        ),
        mainParams = DatabaseMainWeatherParameters(
            0, 0.0, 0.0, 0.0, 0.0, 100, 10.0, 1, 1
        ),
        wind = DatabaseWind(0, 0.0, 0.0, 0.0),
        rain = DatabaseRain(0, 0.0, 0.0),
        snow = DatabaseSnow(0, 0.0, 0.0)
    )

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            WeatherDatabase::class.java
        ).build()

        weatherDao = database.weatherDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertContainerAndGetIt() = runBlocking {
        weatherDao.insertWeatherContainer(container)

        val result = weatherDao.getWeatherContainerById(0)

        assertEquals(container.name, result.name)
    }

    @Test
    @Throws(IOException::class)
    fun insertWeatherExpectWeatherListIsNotEmpty() = runBlocking {
        weatherDao.insertAllWeather(listOf(container.weather))
        val result = weatherDao.getWeatherListById(0)

        assertEquals(true, result.isNotEmpty())
    }

    @Test
    @Throws(IOException::class)
    fun insertMainWeatherAndGetIt() = runBlocking {
        weatherDao.insertMainWeatherParameters(container.mainParams)
        val result = weatherDao.getMainWeatherParametersById(0)

        assertEquals(container.mainParams.temp, result.temp)
    }

    @Test
    @Throws(IOException::class)
    fun insertWindAndGetIt() = runBlocking {
        weatherDao.insertWind(container.wind)
        val result = weatherDao.getWindById(0)

        assertEquals(container.wind.deg, result.deg)
    }

    @Test
    @Throws(IOException::class)
    fun insertRainAndGetIt() = runBlocking {
        weatherDao.insertRain(container.rain)
        val result = weatherDao.getRainById(0)

        assertEquals(container.rain.forLastOneHour, result.forLastOneHour)
    }

    @Test
    @Throws(IOException::class)
    fun insertSnowAndGetIt() = runBlocking {
        weatherDao.insertSnow(container.snow)
        val result = weatherDao.getSnowById(0)

        assertEquals(container.snow.forLastOneHour, result.forLastOneHour)
    }

}