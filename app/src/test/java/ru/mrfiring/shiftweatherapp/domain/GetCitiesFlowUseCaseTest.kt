package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import ru.mrfiring.shiftweatherapp.CoroutineTestRule
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class GetCitiesFlowUseCaseTest {

    private val citiesRepository: CitiesRepository = mockk()
    private val pagingData: PagingData<DomainCity> = mockk()

    private val getCitiesFlowUseCase = GetCitiesFlowUseCase(citiesRepository)
    private val mockFlow: Flow<PagingData<DomainCity>> = mockk()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @ExperimentalPagingApi
    @Test
    fun `get cities flow EXPECT to return pagingData`() = runBlockingTest {
        coEvery { citiesRepository.getCitiesFlow() } returns mockFlow

        val result = getCitiesFlowUseCase()
        coVerify { citiesRepository.getCitiesFlow() }
        assertEquals(mockFlow, result)
    }

}
