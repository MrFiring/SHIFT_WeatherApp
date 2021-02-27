package ru.mrfiring.shiftweatherapp.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mrfiring.shiftweatherapp.repository.network.CitiesParser
import ru.mrfiring.shiftweatherapp.repository.network.City
import ru.mrfiring.shiftweatherapp.repository.network.OpenWeatherApi
import java.lang.Exception

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _response = MutableLiveData<List<City>>()
    val response: LiveData<List<City>>
    get() = _response


    init{
        refreshDataFromServer()
    }

    private fun refreshDataFromServer() = viewModelScope.launch {
        try{
            val responseBody = OpenWeatherApi.openWeatherService.getCitiesFile()
            val parser = CitiesParser()
            val rawData = parser.decompressGZip(responseBody)
            val list = parser.parseJson(rawData)

            _response.value = list

        }catch (ex: Exception){
            Log.e("HomeViewModel", ex.toString())
        }
    }

}