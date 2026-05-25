package ru.mrfiring.shiftweatherapp.presentation.countries

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetCountriesUseCase

interface CountriesComponent {

    val countries: Value<List<String>>

    fun onCountryClick(country: String)
}

class CountriesComponentImpl(
    private val componentContext: ComponentContext,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val countryClickHandler: (String) -> Unit,
) : CountriesComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _countries = MutableValue<List<String>>(emptyList())
    override val countries: Value<List<String>> = _countries

    init {
        scope.launch {
            _countries.value = getCountriesUseCase()
        }
    }

    override fun onCountryClick(country: String) {
        countryClickHandler(country)
    }
}