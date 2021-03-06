package ru.mrfiring.shiftweatherapp.di

import androidx.paging.ExperimentalPagingApi
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesScreenViewModel
import ru.mrfiring.shiftweatherapp.presentation.countries.CountriesScreenViewModel
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailViewModel
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteScreenViewModel

@ExperimentalPagingApi
val viewModelModule = module{
    viewModel { (country: String) ->
        CitiesScreenViewModel(androidApplication(), country, get(), get(), get())
    }

    viewModel {
        CountriesScreenViewModel(androidApplication(), get())
    }

    viewModel {
        FavoriteScreenViewModel(androidApplication(), get(), get())
    }

    viewModel { (cityId: Long) ->
        DetailViewModel(cityId, androidApplication(), get(), get())
    }
}