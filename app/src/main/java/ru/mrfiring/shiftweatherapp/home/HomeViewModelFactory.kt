package ru.mrfiring.shiftweatherapp.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    @ExperimentalPagingApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}