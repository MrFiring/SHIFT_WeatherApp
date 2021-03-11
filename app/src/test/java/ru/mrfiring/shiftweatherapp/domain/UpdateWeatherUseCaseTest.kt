package ru.mrfiring.shiftweatherapp.domain

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import ru.mrfiring.shiftweatherapp.CoroutineTestRule

@ExperimentalCoroutinesApi
class UpdateWeatherUseCaseTest {
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val weatherRepository: WeatherRepository = mockk()
    private val updateWeatherUseCase = UpdateWeatherUseCase(weatherRepository)

    @Test
    fun `update weather EXPECTED to call weather repository update weather`() = runBlockingTest {
        coEvery { weatherRepository.updateWeatherFromServer(0) } just runs

        updateWeatherUseCase(0)

        coVerify { weatherRepository.updateWeatherFromServer(0) }
    }

}