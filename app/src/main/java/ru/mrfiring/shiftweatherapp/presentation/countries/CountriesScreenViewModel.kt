package ru.mrfiring.shiftweatherapp.presentation.countries

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetCountriesUseCase

class CountriesScreenViewModel(
    application: Application,
    private val getCountriesUseCase: GetCountriesUseCase
) : AndroidViewModel(application) {

    private var _countries = MutableLiveData<List<String>>()
    val countries: LiveData<List<String>>
        get() = _countries


    init {
        viewModelScope.launch {
            _countries.value = getCountriesUseCase()
        }

    }

}
