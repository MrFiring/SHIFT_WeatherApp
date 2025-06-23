package ru.mrfiring.shiftweatherapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.location.GetCurrentLocationUseCase
import ru.mrfiring.shiftweatherapp.domain.location.Location
import ru.mrfiring.shiftweatherapp.domain.models.DomainWeatherContainer

interface DetailComponent {

    val status: LiveData<ApiStatus>

    val container: LiveData<DomainWeatherContainer>

    fun onRetryPressed()
}

class DetailComponentImpl(
    private val componentContext: ComponentContext,
    private val cityId: Long?,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
) : DetailComponent, ComponentContext by componentContext {

    private val _status = MutableLiveData<ApiStatus>()
    override val status: LiveData<ApiStatus> = _status

    private val _container = MutableLiveData<DomainWeatherContainer>()
    override val container: LiveData<DomainWeatherContainer> = _container

    private val scope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        refreshWeather()
    }

    private fun refreshWeather() {
        scope.launch {
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

    override fun onRetryPressed() {
        refreshWeather()
    }
}