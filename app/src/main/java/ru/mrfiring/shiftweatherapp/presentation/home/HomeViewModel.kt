package ru.mrfiring.shiftweatherapp.presentation.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.mrfiring.shiftweatherapp.domain.DomainCity
import ru.mrfiring.shiftweatherapp.domain.GetCitiesFlowableUseCase
import ru.mrfiring.shiftweatherapp.presentation.BaseViewModel
import ru.mrfiring.shiftweatherapp.presentation.SingleLiveEvent
import ru.mrfiring.shiftweatherapp.presentation.detail.ApiStatus


@ExperimentalPagingApi
class HomeViewModel(
    application: Application,
    private val getCitiesFlowableUseCase: GetCitiesFlowableUseCase
) : BaseViewModel(application) {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _navigateToDetails = SingleLiveEvent<DomainCity?>()
    val navigateToDetails: LiveData<DomainCity?>
        get() = _navigateToDetails

    private var _cities = MutableLiveData<PagingData<DomainCity>>()
    val cities: LiveData<PagingData<DomainCity>>
        get() = _cities

    init {
        bindData()
    }


    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    private fun bindData() {
        getCitiesFlowableUseCase().cachedIn(viewModelScope)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _cities.value = it
            }, {
                it.printStackTrace()
            })
            .untilDestroy()
    }

    fun onCityClicked(city: DomainCity) {
        _navigateToDetails.value = city
    }

    fun onLoadStateEvent(refreshLoadState: LoadState) {
        _status.value =
            if (refreshLoadState is LoadState.Loading && _status.value != ApiStatus.LOADING) {
                ApiStatus.LOADING
            } else if (refreshLoadState is LoadState.Error && _status.value != ApiStatus.ERROR) {
                ApiStatus.ERROR
            } else {
                ApiStatus.DONE
            }

    }


}