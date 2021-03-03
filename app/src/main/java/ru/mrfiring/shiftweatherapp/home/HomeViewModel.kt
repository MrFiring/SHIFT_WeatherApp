package ru.mrfiring.shiftweatherapp.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ru.mrfiring.shiftweatherapp.SingleLiveEvent
import ru.mrfiring.shiftweatherapp.detail.ApiStatus
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity
import ru.mrfiring.shiftweatherapp.repository.getRepository


@ExperimentalPagingApi
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
    get() = _status




    private val _navigateToDetails = SingleLiveEvent<DomainCity?>()
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
    private fun bindData() {
            _cities = repository.getCitiesLiveData().cachedIn(viewModelScope) as MutableLiveData<PagingData<DomainCity>>
    }

    fun onCityClicked(city: DomainCity) {
        _navigateToDetails.value = city
    }

    fun onLoadStateEvent(refreshLoadState: LoadState) {
        _status.value = if(refreshLoadState is LoadState.Loading && _status.value != ApiStatus.LOADING){
            ApiStatus.LOADING
        }else if(refreshLoadState is LoadState.Error && _status.value != ApiStatus.ERROR){
            ApiStatus.ERROR
        }else{
            ApiStatus.DONE
        }

    }



}