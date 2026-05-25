package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.ExperimentalPagingApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.mrfiring.shiftweatherapp.CoroutineTestRule
import ru.mrfiring.shiftweatherapp.data.CitiesRepositoryImpl
import ru.mrfiring.shiftweatherapp.data.asDatabaseObject
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.database.model.DatabaseCity
import ru.mrfiring.shiftweatherapp.data.paging.CityMediator
import ru.mrfiring.shiftweatherapp.domain.models.City

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class CitiesRepositoryTest {
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val citiesDao: CitiesDao = mockk()
    private val cityMediator: CityMediator = mockk()
    private val city: City = City(
        0, "",
        "", "",
        0.0, 0.0,
        false
    )

    private val favorites: List<DatabaseCity> = listOf(city.asDatabaseObject())

    private val citiesRepository = CitiesRepositoryImpl(citiesDao, cityMediator)

    @Test
    fun `get favorite cities EXPECTED to call citiesDao favorite cities`() = runBlockingTest {
        coEvery { citiesDao.getFavoriteCities() } returns favorites

        citiesRepository.getFavoriteCities()

        coVerify { citiesDao.getFavoriteCities() }
    }

    @Test
    fun `update city EXPECTED to call dao update city`() = runBlockingTest {
        coEvery { citiesDao.updateCity(city.asDatabaseObject()) } just runs

        citiesRepository.updateCity(city)

        coVerify { citiesDao.updateCity(city.asDatabaseObject()) }
    }
}