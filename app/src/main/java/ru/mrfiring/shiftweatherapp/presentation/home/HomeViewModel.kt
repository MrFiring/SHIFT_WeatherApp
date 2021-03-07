package ru.mrfiring.shiftweatherapp.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.mrfiring.shiftweatherapp.domain.GetCitiesLiveDataUseCase
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

@ExperimentalPagingApi
class HomeViewModel(application: Application,
                    private val getCitiesFlowUseCase: GetCitiesLiveDataUseCase
) : AndroidViewModel(application) {

    private lateinit var _cities: Flow<PagingData<DomainCity>>
    val cities: Flow<PagingData<DomainCity>>
        get() = _cities

    init {
        bindData()
    }

    @ExperimentalPagingApi
    private fun bindData() {
        _cities = getCitiesFlowUseCase().cachedIn(viewModelScope)
    }

}