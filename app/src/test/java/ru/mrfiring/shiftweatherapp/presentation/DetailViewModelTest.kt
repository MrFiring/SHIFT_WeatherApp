package ru.mrfiring.shiftweatherapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule
import ru.mrfiring.shiftweatherapp.CoroutineTestRule

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

//    @Test
//    fun `init block EXPECTED to retrieve weather container`(){
//
//        val viewModel = DetailViewModel()
//
//    }

}