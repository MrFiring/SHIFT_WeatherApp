package ru.mrfiring.shiftweatherapp.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.repository.WeatherRepository
import ru.mrfiring.shiftweatherapp.repository.domain.DomainWeatherContainer

class DetailViewModel(private val cityId: Long, application: Application, private val repository: WeatherRepository) : AndroidViewModel(application) {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
    get() = _status

    private val _container = MutableLiveData<DomainWeatherContainer>()
    val container: LiveData<DomainWeatherContainer>
    get() = _container

    val formattedTemperature: LiveData<String> = Transformations.map(_container){
        application.resources.getString(R.string.temp_format, it.mainParams.temp)
    }
    val formattedFeelsTemperature: LiveData<String> = Transformations.map(_container){
        application.resources.getString(R.string.temp_feels_format, it.mainParams.feelsLike)
    }
    val formattedMaxTemperature: LiveData<String> = Transformations.map(_container){
        application.resources.getString(R.string.temp_format, it.mainParams.tempMax)
    }
    val formattedMinTemperature: LiveData<String> = Transformations.map(_container){
        application.resources.getString(R.string.temp_format, it.mainParams.tempMin)
    }
    val formattedHumidity: LiveData<String> = Transformations.map(_container){
        application.resources.getString(R.string.humidity_format, it.mainParams.humidity)
    }
    val formattedPressure: LiveData<String> = Transformations.map(_container){
        application.resources.getString(R.string.pressure_format, it.mainParams.pressure)
    }


    init{
        refreshWeather()
    }

    private fun refreshWeather() = viewModelScope.launch {
        try {
                _status.value = ApiStatus.LOADING
                repository.updateWeatherFromServer(cityId)
                _container.value = repository.getWeather(cityId)
                _status.value = ApiStatus.DONE
        }
        catch (ex: Exception){
            _status.value = ApiStatus.ERROR
            Log.e("DetailViewModel", ex.stackTraceToString())
        }

    }

}