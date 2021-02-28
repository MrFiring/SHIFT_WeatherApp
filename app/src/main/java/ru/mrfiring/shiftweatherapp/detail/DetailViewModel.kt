package ru.mrfiring.shiftweatherapp.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.repository.domain.DomainWeatherContainer
import ru.mrfiring.shiftweatherapp.repository.getRepository

class DetailViewModel(application: Application, val cityId: Long) : AndroidViewModel(application) {
    val container = MutableLiveData<DomainWeatherContainer>()
    val repository = getRepository(application.applicationContext)


    init{
        refreshWeather()
    }

    private fun refreshWeather() = viewModelScope.launch {
        try {
                repository.updateWeatherFromServer(cityId)
                container.value = repository.getWeather(cityId)
        }
        catch (ex: Exception){
            Log.e("DetailViewModel", ex.stackTraceToString())
        }

    }

}