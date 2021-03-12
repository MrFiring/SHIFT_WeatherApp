package ru.mrfiring.shiftweatherapp.presentation

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.mrfiring.shiftweatherapp.CoroutineTestRule
import ru.mrfiring.shiftweatherapp.domain.CitiesRepository
import ru.mrfiring.shiftweatherapp.domain.GetCitiesFlowUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesScreenViewModel

@ExperimentalCoroutinesApi
class CitiesViewModelTest {
    private val application: Application = mockk()

    private val citiesRepository: CitiesRepository = mockk()

    private val getCitiesFlowUseCase: GetCitiesFlowUseCase = mockk()
    private val updateCityUseCase: UpdateCityUseCase = UpdateCityUseCase(citiesRepository)
    private val pagingFlow: Flow<PagingData<DomainCity>> = mockk()

    @ExperimentalPagingApi
    private lateinit var citiesScreenViewModel: CitiesScreenViewModel

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val domainCity: DomainCity = DomainCity(
        15,
        "",
        "",
        "",
        15.0,
        40.0,
        false
    )

    @ExperimentalPagingApi
    @Before
    fun setupVM() {
        coEvery { getCitiesFlowUseCase() } returns pagingFlow

        citiesScreenViewModel = CitiesScreenViewModel(
            application,
            getCitiesFlowUseCase,
            updateCityUseCase
        )
    }

    @ExperimentalPagingApi
    @Test
    fun `update city EXPECTED to call useCase update city and invert favorite`() = runBlockingTest {
        coEvery { updateCityUseCase(domainCity) } just runs

        citiesScreenViewModel.onLongPress(domainCity)

        coVerify { updateCityUseCase(domainCity) }
        assertEquals(true, domainCity.favorite)
    }

}