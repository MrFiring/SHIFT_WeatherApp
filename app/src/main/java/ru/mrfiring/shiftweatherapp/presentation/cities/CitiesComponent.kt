package ru.mrfiring.shiftweatherapp.presentation.cities

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetCitiesByCountryFlowUseCase
import ru.mrfiring.shiftweatherapp.domain.GetCitiesFlowUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateCityUseCase
import ru.mrfiring.shiftweatherapp.domain.models.City

interface CitiesComponent {

    val cities: Flow<PagingData<City>>

    fun onLongPress(city: City)

    fun onSelectCity(city: City)

}

@ExperimentalPagingApi
class CitiesComponentImpl(
    private val componentContext: ComponentContext,
    private val country: String,
    private val onCitySelect: (City) -> Unit,
    private val getCitiesFlowUseCase: GetCitiesFlowUseCase,
    private val getCitiesByCountryFlowUseCase: GetCitiesByCountryFlowUseCase,
    private val updateCityUseCase: UpdateCityUseCase
) : CitiesComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var _cities: Flow<PagingData<City>> = emptyFlow()
    override val cities: Flow<PagingData<City>>
        get() = _cities

    init {
        scope.launch {
            bindData()
        }
    }

    @ExperimentalPagingApi
    private suspend fun bindData() {
        val citiesFlow = if (country.isEmpty()) {
            getCitiesFlowUseCase().cachedIn(scope)
        } else {
            getCitiesByCountryFlowUseCase(country).cachedIn(scope)
        }
        _cities = citiesFlow.stateIn(scope)
    }

    override fun onLongPress(city: City) {
        scope.launch {
            city.favorite = !city.favorite
            updateCityUseCase(city)
        }
    }

    override fun onSelectCity(city: City) {
        this.onCitySelect(city)
    }

}