package ru.mrfiring.shiftweatherapp.presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.GetCityByIdUseCase
import ru.mrfiring.shiftweatherapp.domain.GetWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.UpdateWeatherUseCase
import ru.mrfiring.shiftweatherapp.domain.location.GetCurrentLocationUseCase
import ru.mrfiring.shiftweatherapp.domain.location.Location
import ru.mrfiring.shiftweatherapp.domain.location.NoLocationPermission
import ru.mrfiring.shiftweatherapp.domain.models.City

interface DetailComponent {

    val state: Value<DetailState>

    fun onRetryPressed()
}

class DetailComponentImpl(
    private val componentContext: ComponentContext,
    private val cityId: Long?,
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getCityByIdUseCase: GetCityByIdUseCase,
) : DetailComponent, ComponentContext by componentContext {

    private val _state = MutableValue<DetailState>(DetailState.Loading)
    override val state: Value<DetailState> = _state

    private val scope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        refreshWeather()
    }

    private fun refreshWeather() {
        scope.launch {
            try {
                _state.value = DetailState.Loading

                if (cityId != null) {
                    updateWeatherUseCase(cityId)
                    _state.value = DetailState.Content(
                        container = getWeatherUseCase(cityId),
                        city = getCityByIdUseCase(cityId)
                    )
                } else {
                    val location: Location? = getCurrentLocationUseCase()
                    if (location == null) {
                        _state.value = DetailState.Error
                        return@launch
                    }
                    _state.value = DetailState.Content(
                        container = getWeatherUseCase(location),
                        city = null
                    )
                }
            } catch (ex: NoLocationPermission) {
                _state.value = DetailState.LocationPermissionMissing
            } catch (ex: Exception) {
                _state.value = DetailState.Error
            }
        }
    }

    override fun onRetryPressed() {
        refreshWeather()
    }
}
