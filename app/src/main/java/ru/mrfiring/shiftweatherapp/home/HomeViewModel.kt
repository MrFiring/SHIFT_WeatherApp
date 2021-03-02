package ru.mrfiring.shiftweatherapp.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity
import ru.mrfiring.shiftweatherapp.repository.getRepository

@ExperimentalPagingApi
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToDetails = MutableLiveData<DomainCity?>()
    val navigateToDetails: LiveData<DomainCity?>
    get() = _navigateToDetails

    private val repository = getRepository(application.applicationContext)

    private var _cities = MutableLiveData<PagingData<DomainCity>>()
    val cities: LiveData<PagingData<DomainCity>>
    get() = _cities


    init{
        bindData()
    }


    @ExperimentalPagingApi
    private fun bindData() = viewModelScope.launch {
            _cities = repository.getCitiesLiveData().cachedIn(viewModelScope) as MutableLiveData<PagingData<DomainCity>>
    }

    fun onCityClicked(city: DomainCity) {
        _navigateToDetails.value = city
    }

    fun doneNavigating(){
        _navigateToDetails.value = null
    }

}