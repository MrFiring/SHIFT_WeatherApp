package ru.mrfiring.shiftweatherapp.presentation.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetCountriesUseCase

interface CountriesComponent {

    val countries: LiveData<List<String>>

    fun onCountryClick(country: String)
}

class CountriesComponentImpl(
    private val componentContext: ComponentContext,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val countryClickHandler: (String) -> Unit,
) : CountriesComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _countries = MutableLiveData<List<String>>()
    override val countries: LiveData<List<String>> = _countries

    init {
        scope.launch {
            _countries.value = getCountriesUseCase()
        }
    }

    override fun onCountryClick(country: String) {
        countryClickHandler(country)
    }
}