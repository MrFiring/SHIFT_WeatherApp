package ru.mrfiring.shiftweatherapp.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.mrfiring.shiftweatherapp.CoroutineTestRule
import ru.mrfiring.shiftweatherapp.domain.GetWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.models.DomainWeatherContainer
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailViewModel

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val application: Application = mockk()
    private val getWeatherUseCase: GetWeatherUseCase = mockk()
    private val updateWeatherUseCase: UpdateWeatherUseCase = mockk()

    private val container: DomainWeatherContainer = mockk()
    private val cityId: Long = 0

    @Test
    fun `init block EXPECTED to retrieve weather container`() = runBlocking {
        coEvery { getWeatherUseCase(cityId) } returns container
        coEvery { updateWeatherUseCase(cityId) } just runs

        val viewModel = DetailViewModel(
            cityId,
            application,
            updateWeatherUseCase,
            getWeatherUseCase
        )

        coVerify { getWeatherUseCase(cityId) }
        coVerify { updateWeatherUseCase(cityId) }
        assertEquals(container, viewModel.container.value)
    }

}