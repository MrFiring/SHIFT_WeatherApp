package ru.mrfiring.shiftweatherapp.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

@ObsoleteCoroutinesApi
class GetCitiesFlowUseCaseTest {

    private val citiesRepository: CitiesRepository = mockk()
    private val pagingData: PagingData<DomainCity> = mockk()

    private val getCitiesFlowUseCase = GetCitiesFlowUseCase(citiesRepository)
    private val mockFlow: Flow<PagingData<DomainCity>> = mockk()

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

    @ExperimentalPagingApi
    @Test
    fun `get cities flow EXPECT to return pagingData`() = runBlocking {
        coEvery { citiesRepository.getCitiesFlow() } returns mockFlow

        val result = getCitiesFlowUseCase()
        coVerify { citiesRepository.getCitiesFlow() }
        assertEquals(mockFlow, result)
    }

}
