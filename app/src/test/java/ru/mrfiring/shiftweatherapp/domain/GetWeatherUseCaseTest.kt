package ru.mrfiring.shiftweatherapp.domain

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.mrfiring.shiftweatherapp.domain.models.DomainWeatherContainer

@ObsoleteCoroutinesApi
class GetWeatherUseCaseTest {

    private val weatherRepository: WeatherRepository = mockk()
    private val domainWeatherContainer: DomainWeatherContainer = mockk()

    private val getWeatherUseCase = GetWeatherUseCase(weatherRepository)


    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `get weather EXPECTED to return weather container`() = runBlocking {
        coEvery { weatherRepository.getWeather(capture(slot())) } returns domainWeatherContainer

        val result = getWeatherUseCase(0)

        assertEquals(domainWeatherContainer, result)
    }


}