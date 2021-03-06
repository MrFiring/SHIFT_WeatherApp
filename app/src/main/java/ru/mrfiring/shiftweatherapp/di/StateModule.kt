package ru.mrfiring.shiftweatherapp.di

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.koin.dsl.module

val stateModule = module {
    fun provideThemeState(): MutableState<Boolean> {
        return mutableStateOf(true)
    }

    single {
        provideThemeState()
    }
}