package ru.mrfiring.shiftweatherapp

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.mrfiring.shiftweatherapp.di.*

class WeatherApplication: Application() {
    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            modules(
                apiModule,
                databaseModule,
                networkModule,
                repositoryModule,
                viewModelModule,
                useCaseModule
            )
        }
    }
}