package ru.mrfiring.shiftweatherapp.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.repository.WeatherRepository
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity
import ru.mrfiring.shiftweatherapp.repository.domain.asDomainObject
import ru.mrfiring.shiftweatherapp.repository.getRepository
import ru.mrfiring.shiftweatherapp.repository.network.*
import java.lang.Exception

enum class ApiStatus { LOADING, ERROR, DONE}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
    get() = _status

    private val _navigateToDetails = MutableLiveData<DomainCity?>()
    val navigateToDetails: LiveData<DomainCity?>
    get() = _navigateToDetails

    private val repository = getRepository(application.applicationContext)
    val cities = repository.cities


    init{
        refreshDataFromServer()
    }


    private fun refreshDataFromServer() = viewModelScope.launch {
        try{

            if(cities.value.isNullOrEmpty()) {
                _status.value = ApiStatus.LOADING
                repository.loadCitiesFromServer()
                _status.value = ApiStatus.DONE
            }
        }catch (ex: Exception){
            _status.value = ApiStatus.ERROR
            Log.e("HomeViewModel", ex.toString())
        }
    }

    fun onCityClicked(city: DomainCity) {
        _navigateToDetails.value = city
    }

    fun doneNavigating(){
        _navigateToDetails.value = null
    }

    fun isDataLoaded(){
        _status.value = ApiStatus.DONE
    }

}