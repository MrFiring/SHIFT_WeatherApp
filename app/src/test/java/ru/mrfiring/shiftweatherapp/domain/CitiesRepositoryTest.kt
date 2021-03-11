package ru.mrfiring.shiftweatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.mrfiring.shiftweatherapp.CoroutineTestRule
import ru.mrfiring.shiftweatherapp.data.CitiesRepositoryImpl
import ru.mrfiring.shiftweatherapp.data.asDatabaseObject
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.database.DatabaseCity
import ru.mrfiring.shiftweatherapp.data.paging.CityMediator
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class CitiesRepositoryTest {
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val citiesDao: CitiesDao = mockk()
    private val cityMediator: CityMediator = mockk()
    private val city: DomainCity = DomainCity(
        0, "",
        "", "",
        0.0, 0.0,
        false
    )

    private val liveDataFavorites: LiveData<List<DatabaseCity>> = mockk()

    private val citiesRepository = CitiesRepositoryImpl(citiesDao, cityMediator)


//    @Test
//    fun `get favorite cities EXPECTED to call get favorite cities method of dao`() = runBlockingTest {
//        coEvery { citiesDao.getFavoriteCities() } returns liveDataFavorites
//        withContext(Dispatchers.Main) {
//            val result = citiesRepository.getFavoriteCities()
//            coVerify { citiesDao.getFavoriteCities() }
//            assertEquals()
//        }
//
//
//    }


    @Test
    fun `update city EXPECTED to call dao update city`() = runBlockingTest {
        coEvery { citiesDao.updateCity(city.asDatabaseObject()) } just runs

        citiesRepository.updateCity(city)

        coVerify { citiesDao.updateCity(city.asDatabaseObject()) }
    }
}