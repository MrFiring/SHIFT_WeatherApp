package ru.mrfiring.shiftweatherapp.di

import androidx.paging.ExperimentalPagingApi
import com.squareup.moshi.Moshi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.mrfiring.shiftweatherapp.data.CitiesParser
import ru.mrfiring.shiftweatherapp.data.CitiesParserImpl
import ru.mrfiring.shiftweatherapp.data.database.CitiesDao
import ru.mrfiring.shiftweatherapp.data.network.BASE_URL
import ru.mrfiring.shiftweatherapp.data.network.OpenWeatherService
import ru.mrfiring.shiftweatherapp.data.paging.CityMediator

@ExperimentalPagingApi
val networkModule = module {
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun provideMoshi(): Moshi = Moshi.Builder().build()

    fun provideParser(moshi: Moshi): CitiesParser = CitiesParserImpl(moshi)

    fun provideMediator(weatherService: OpenWeatherService,
                        citiesDao: CitiesDao,
                        citiesParser: CitiesParser
    ): CityMediator {
        return CityMediator(weatherService, citiesDao, citiesParser)
    }

    single {provideRetrofit(BASE_URL)}
    factory {provideMoshi()}
    factory { provideParser(get()) }
    factory { provideMediator(get(), get(), get()) }

}