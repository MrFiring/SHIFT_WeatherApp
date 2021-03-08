package ru.mrfiring.shiftweatherapp.presentation.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.DomainWeatherContainer
import ru.mrfiring.shiftweatherapp.domain.GetWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateWeatherUseCase
import ru.mrfiring.shiftweatherapp.presentation.BaseViewModel

class DetailViewModel(
    private val cityId: Long,
    application: Application,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val getWeatherUseCase: GetWeatherUseCase
) : BaseViewModel(application) {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _container = MutableLiveData<DomainWeatherContainer>()
    val container: LiveData<DomainWeatherContainer>
        get() = _container

    val formattedTemperature: LiveData<String> = Transformations.map(_container) {
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
               updateWeatherUseCase(cityId)
                _container.value = getWeatherUseCase(cityId)!!
                _status.value = ApiStatus.DONE
        }
        catch (ex: Exception){
            _status.value = ApiStatus.ERROR
            Log.e("DetailViewModel", ex.stackTraceToString())
        }

    }

}