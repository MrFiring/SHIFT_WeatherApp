package ru.mrfiring.shiftweatherapp.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val application: Application,
    private val cityId: Long
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(application, cityId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}