package ru.mrfiring.shiftweatherapp.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import ru.mrfiring.shiftweatherapp.CoroutineTestRule
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

@ExperimentalCoroutinesApi
class GetFavoriteCitiesLiveDataUseCaseTest {


    private val citiesRepository: CitiesRepository = mockk()
    private val city: DomainCity = mockk()

    private val getFavoriteCitiesLiveDataUseCase =
        GetFavoriteCitiesLiveDataUseCase(citiesRepository)

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Test
    fun `get favorite cities EXPECTED to return list of cities`() = runBlockingTest {
        coEvery { citiesRepository.getFavoriteCities().value } returns listOf(city)

        val cities = getFavoriteCitiesLiveDataUseCase()

        coVerify { getFavoriteCitiesLiveDataUseCase() }
        assertEquals(listOf(city), cities.value)
    }

}