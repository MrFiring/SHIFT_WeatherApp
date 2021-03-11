package ru.mrfiring.shiftweatherapp.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

@ExperimentalCoroutinesApi
class GetFavoriteCitiesLiveDataUseCaseTest {


    private val citiesRepository: CitiesRepository = mockk()
    private val city: DomainCity = mockk()

    private val getFavoriteCitiesLiveDataUseCase =
        GetFavoriteCitiesLiveDataUseCase(citiesRepository)

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `get favorite cities EXPECTED to return list of cities`() = runBlocking {
        coEvery { citiesRepository.getFavoriteCities().value } returns listOf(city)

        val cities = getFavoriteCitiesLiveDataUseCase()

        coVerify { getFavoriteCitiesLiveDataUseCase() }
        assertEquals(listOf(city), cities.value)
    }

}