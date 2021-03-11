package ru.mrfiring.shiftweatherapp.domain

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import ru.mrfiring.shiftweatherapp.CoroutineTestRule
import ru.mrfiring.shiftweatherapp.domain.models.DomainWeatherContainer

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class GetWeatherUseCaseTest {

    private val weatherRepository: WeatherRepository = mockk()
    private val domainWeatherContainer: DomainWeatherContainer = mockk()

    private val getWeatherUseCase = GetWeatherUseCase(weatherRepository)


    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Test
    fun `get weather EXPECTED to return weather container`() = runBlockingTest {
        coEvery { weatherRepository.getWeather(capture(slot())) } returns domainWeatherContainer

        val result = getWeatherUseCase(0)

        assertEquals(domainWeatherContainer, result)
    }


}