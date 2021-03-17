package ru.mrfiring.shiftweatherapp.presentation.cities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetCitiesByCountryFlowUseCase
import ru.mrfiring.shiftweatherapp.domain.GetCitiesFlowUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity

@ExperimentalPagingApi
class CitiesScreenViewModel(
    application: Application,
    private val country: String,
    private val getCitiesFlowUseCase: GetCitiesFlowUseCase,
    private val getCitiesByCountryFlowUseCase: GetCitiesByCountryFlowUseCase,
    private val updateCityUseCase: UpdateCityUseCase
) : AndroidViewModel(application) {

    private lateinit var _cities: Flow<PagingData<DomainCity>>
    val cities: Flow<PagingData<DomainCity>>
        get() = _cities

    init {
        bindData()
    }

    @ExperimentalPagingApi
    private fun bindData() {
        _cities = if (country.isEmpty()) {
            getCitiesFlowUseCase().cachedIn(viewModelScope)
        } else {
            getCitiesByCountryFlowUseCase(country).cachedIn(viewModelScope)
        }
    }

    fun onLongPress(city: DomainCity) = viewModelScope.launch {
        city.favorite = !city.favorite
        updateCityUseCase(city)
    }

}