package ru.mrfiring.shiftweatherapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    fun Disposable.untilDestroy() = compositeDisposable.add(this)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}