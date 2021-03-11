package ru.mrfiring.shiftweatherapp.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.database.DatabaseCity
import ru.mrfiring.shiftweatherapp.data.database.WeatherDatabase
import ru.mrfiring.shiftweatherapp.getOrAwaitValue
import java.io.IOException

@ExperimentalCoroutinesApi
class CitiesDaoTest {
    private lateinit var citiesDao: CitiesDao
    private lateinit var database: WeatherDatabase

    private val cities = listOf(
        DatabaseCity(1, "hello", "", "Us", 0.0, 0.0, false),
        DatabaseCity(2, "hello", "", "Us", 0.0, 0.0, true),
        DatabaseCity(3, "hello", "", "Us", 0.0, 0.0, false)
    )

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            WeatherDatabase::class.java
        ).build()

        citiesDao = database.citiesDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertCitiesAndGetCountOfThem() = runBlocking {
        citiesDao.insertCities(cities)
        val count = citiesDao.getCountOfCities()
        assertEquals(cities.size, count)
    }

    @Test
    @Throws(IOException::class)
    fun insertSomeCityUpdateItAndCheck() = runBlocking {
        val itemToReplace = cities[0].copy(favorite = true)
        citiesDao.insertCities(cities)

        citiesDao.updateCity(itemToReplace)

        val result = citiesDao.getCityById(1)

        assertEquals(itemToReplace.favorite, result.favorite)

    }

    //doesn't work
    @Test
    @Throws(IOException::class)
    fun insertCitiesAndGetFavoriteLiveData() = runBlocking {
        citiesDao.insertCities(cities)

        withContext(Dispatchers.Main) {
            val result = citiesDao.getFavoriteCities().getOrAwaitValue()

            assertEquals(
                cities.filter { it.favorite }.size,
                result.size
            )
        }
    }

    @Test
    @Throws(IOException::class)
    fun insertCitiesAndReadInsertedCities() = runBlocking {

        citiesDao.insertCities(cities)
        val firstItem = citiesDao.getCityById(1)
        assertEquals(cities[0], firstItem)
    }

}