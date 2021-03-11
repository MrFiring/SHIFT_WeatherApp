package ru.mrfiring.shiftweatherapp.domain

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import ru.mrfiring.shiftweatherapp.CoroutineTestRule
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class UpdateCityUseCaseTest {

    private val citiesRepository: CitiesRepository = mockk()
    private val updateCityUseCase = UpdateCityUseCase(citiesRepository)
    private val city: DomainCity = mockk()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Test
    fun `update city EXPECTED to call repository update city`() = runBlockingTest {
        coEvery { citiesRepository.updateCity(city) } just runs

        updateCityUseCase(city)

        coVerify { citiesRepository.updateCity(city) }
    }


}