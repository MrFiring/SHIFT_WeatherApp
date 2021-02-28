package ru.mrfiring.shiftweatherapp.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class DetailViewModel(application: Application, cityId: Long) : AndroidViewModel(application) {

    val _city = MutableLiveData<String>()
    val city: LiveData<String>
    get() = _city


    init{
        _city.value = cityId.toString()
    }

}