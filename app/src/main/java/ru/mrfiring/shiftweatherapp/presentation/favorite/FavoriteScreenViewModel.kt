package ru.mrfiring.shiftweatherapp.presentation.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.domain.models.DomainCity
import ru.mrfiring.shiftweatherapp.presentation.detail.ApiStatus

class FavoriteScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val _favorites = MutableLiveData<DomainCity>()
    val favorites: LiveData<DomainCity>
        get() = _favorites

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        bindFavorites()
    }

    private fun bindFavorites() = viewModelScope.launch {
        try {
            _status.value = ApiStatus.LOADING
            //Do work here)
            _status.value = ApiStatus.DONE
        } catch (ex: Exception) {
            //Print ex here)
            _status.value = ApiStatus.ERROR
        }

    }

}