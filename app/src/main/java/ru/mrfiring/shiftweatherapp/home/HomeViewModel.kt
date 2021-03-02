package ru.mrfiring.shiftweatherapp.home

import android.app.Application
import android.util.Log
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
import java.lang.Exception

enum class ApiStatus { LOADING, ERROR, DONE}

@ExperimentalPagingApi
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
    get() = _status

    private val _navigateToDetails = MutableLiveData<DomainCity?>()
    val navigateToDetails: LiveData<DomainCity?>
    get() = _navigateToDetails

    private val repository = getRepository(application.applicationContext)

    private var _cities = MutableLiveData<PagingData<DomainCity>>()
    val cities: LiveData<PagingData<DomainCity>>
    get() = _cities


    init{
        refreshDataFromServer()
    }


    @ExperimentalPagingApi
    private fun refreshDataFromServer() = viewModelScope.launch {
        try {


            _status.value = ApiStatus.LOADING
            _cities = repository.getCitiesLiveData().cachedIn(viewModelScope) as MutableLiveData<PagingData<DomainCity>>
            _status.value = ApiStatus.DONE

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

}