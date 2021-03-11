package ru.mrfiring.shiftweatherapp.presentation

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import ru.mrfiring.shiftweatherapp.CoroutineTestRule
import ru.mrfiring.shiftweatherapp.domain.CitiesRepository
import ru.mrfiring.shiftweatherapp.domain.GetFavoriteCitiesLiveDataUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteScreenViewModel

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val application: Application = mockk()
    private val citiesRepository: CitiesRepository = mockk()
    private val favoriteLiveData: MutableLiveData<List<DomainCity>> = mockk()

    private val getFavoriteCitiesLiveDataUseCase =
        GetFavoriteCitiesLiveDataUseCase(citiesRepository)

    private val updateCityUseCase = UpdateCityUseCase(citiesRepository)

    private val city = DomainCity(
        0, "", "", "", 15.0, 10.0, true
    )


    @Test
    fun `init of VM EXPECTED to call bindFavorites`() = runBlockingTest {
        coEvery { getFavoriteCitiesLiveDataUseCase() } returns favoriteLiveData
        val viewModel = FavoriteScreenViewModel(
            application,
            getFavoriteCitiesLiveDataUseCase,
            updateCityUseCase
        )

        coVerify { getFavoriteCitiesLiveDataUseCase() }

        assertEquals(favoriteLiveData, viewModel.favorites)
    }

    @Test
    fun `call of longPress EXPECTED to callUpdateCityUseCase and invert favorite flag`() =
        runBlockingTest {
            coEvery { getFavoriteCitiesLiveDataUseCase() } returns favoriteLiveData

            coEvery { updateCityUseCase(city) } just runs

            val viewModel = FavoriteScreenViewModel(
                application,
                getFavoriteCitiesLiveDataUseCase,
                updateCityUseCase
            )

            viewModel.onLongPress(city)

            coVerify { updateCityUseCase(city) }

            assertEquals(false, city.favorite)
        }

}