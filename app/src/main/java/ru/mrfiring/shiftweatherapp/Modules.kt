package ru.mrfiring.shiftweatherapp

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.mrfiring.shiftweatherapp.detail.DetailViewModel
import ru.mrfiring.shiftweatherapp.home.HomeViewModel
import ru.mrfiring.shiftweatherapp.repository.WeatherRepository
import ru.mrfiring.shiftweatherapp.repository.database.WeatherDatabase
import ru.mrfiring.shiftweatherapp.repository.network.BASE_URL
import ru.mrfiring.shiftweatherapp.repository.network.CitiesParser
import ru.mrfiring.shiftweatherapp.repository.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.repository.paging.CityMediator

val apiModule = module{

    fun provideOpenWeatherService(retrofit: Retrofit): OpenWeatherService{
        return retrofit.create(OpenWeatherService::class.java)
    }

    single { provideOpenWeatherService(get())}
}

val databaseModule = module {
    fun provideWeatherDatabase(application: Application): WeatherDatabase{
        return Room.databaseBuilder(
                application.applicationContext,
                WeatherDatabase::class.java, "weather_db"
        )
                .fallbackToDestructiveMigration()
                .build()
    }

    single {provideWeatherDatabase(get())}
}

@ExperimentalPagingApi
val networkModule = module {
    fun provideRetrofit(baseUrl: String): Retrofit{
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    fun provideMoshi(): Moshi = Moshi.Builder().build()

    fun provideParser(moshi: Moshi): CitiesParser = CitiesParser(moshi)

    fun provideMediator(weatherService: OpenWeatherService,
                             database: WeatherDatabase,
                            citiesParser: CitiesParser): CityMediator {
        return CityMediator(weatherService, database, citiesParser)
    }

    single {provideRetrofit(BASE_URL)}
    factory {provideMoshi()}
    factory { provideParser(get()) }
    factory { provideMediator(get(), get(), get()) }

}

@ExperimentalPagingApi
val repositoryModule = module {

    fun provideWeatherRepository(database: WeatherDatabase,
                                 networkService: OpenWeatherService,
                                 cityMediator: CityMediator
    ): WeatherRepository{
        return WeatherRepository(database, networkService, cityMediator)
    }

    single {provideWeatherRepository(get(), get(), get())}
}

@ExperimentalPagingApi
val viewModelModule = module{
     viewModel{
         HomeViewModel(androidApplication(), get())
    }

    viewModel {
            (cityId: Long) -> DetailViewModel(cityId, androidApplication(), get())
    }
}