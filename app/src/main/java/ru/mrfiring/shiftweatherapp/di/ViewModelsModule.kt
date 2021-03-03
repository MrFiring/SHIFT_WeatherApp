package ru.mrfiring.shiftweatherapp.di

import androidx.paging.ExperimentalPagingApi
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.detail.DetailViewModel
import ru.mrfiring.shiftweatherapp.home.HomeViewModel

@ExperimentalPagingApi
val viewModelModule = module{
    viewModel{
        HomeViewModel(androidApplication(), get())
    }

    viewModel {
            (cityId: Long) -> DetailViewModel(cityId, androidApplication(), get())
    }
}