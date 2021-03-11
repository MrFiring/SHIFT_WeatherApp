package ru.mrfiring.shiftweatherapp.domain

import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

@ObsoleteCoroutinesApi
class UpdateCityUseCaseTest {

    private val citiesRepository: CitiesRepository = mockk()
    private val updateCityUseCase = UpdateCityUseCase(citiesRepository)
    private val city: DomainCity = mockk()

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
    fun `update city EXPECTED to call repository update city`() = runBlocking {
        coEvery { citiesRepository.updateCity(city) } just runs

        updateCityUseCase(city)

        coVerify { citiesRepository.updateCity(city) }
    }


}