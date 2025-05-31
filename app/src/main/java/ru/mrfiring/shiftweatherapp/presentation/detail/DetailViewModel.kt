package ru.mrfiring.shiftweatherapp.presentation.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.location.GetCurrentLocationUseCase
import ru.mrfiring.shiftweatherapp.domain.location.Location
import ru.mrfiring.shiftweatherapp.domain.models.DomainWeatherContainer

class DetailViewModel(
    private val cityId: Long?,
    application: Application,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
) : AndroidViewModel(application) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _container = MutableLiveData<DomainWeatherContainer>()
    val container: LiveData<DomainWeatherContainer>
        get() = _container

    init {
        refreshWeather()
    }

    private fun refreshWeather() {
        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING

                if (cityId != null) {
                    updateWeatherUseCase(cityId)
                    _container.value = getWeatherUseCase(cityId)
                } else {
                    val location: Location? = getCurrentLocationUseCase()
                    if (location == null) {
                        _status.value = ApiStatus.ERROR
                        return@launch
                    }
                    _container.value = getWeatherUseCase(location)
                }

                _status.value = ApiStatus.DONE
            } catch (ex: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun onRetryPressed() {
        refreshWeather()
    }
}