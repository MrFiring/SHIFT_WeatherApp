package ru.mrfiring.shiftweatherapp.di

import androidx.paging.ExperimentalPagingApi
import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.get
import org.koin.dsl.module
import ru.mrfiring.shiftweatherapp.domain.models.City
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesComponent
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesComponentImpl
import ru.mrfiring.shiftweatherapp.presentation.countries.CountriesComponent
import ru.mrfiring.shiftweatherapp.presentation.countries.CountriesComponentImpl
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailComponent
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailComponentImpl
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteComponent
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteComponentImpl
import ru.mrfiring.shiftweatherapp.presentation.main.MainComponent
import ru.mrfiring.shiftweatherapp.presentation.main.MainComponentImpl
import ru.mrfiring.shiftweatherapp.presentation.onboarding.OnboardingComponent
import ru.mrfiring.shiftweatherapp.presentation.onboarding.OnboardingComponentImpl

val componentModule = module {

    single { ComponentFactory(getKoin()) }
}

fun ComponentFactory.createMainComponent(
    componentContext: ComponentContext,
    isLocationGranted: () -> Boolean,
    changeThemeHandler: () -> Unit
): MainComponent =
    MainComponentImpl(
        componentContext = componentContext,
        isLocationGranted = isLocationGranted,
        changeThemeHandler = changeThemeHandler,
        componentFactory = get(),
    )

@OptIn(ExperimentalPagingApi::class)
fun ComponentFactory.createCitiesComponent(
    componentContext: ComponentContext,
    country: String,
    onCitySelect: (City) -> Unit,
): CitiesComponent =
    CitiesComponentImpl(
        componentContext = componentContext,
        country = country,
        onCitySelect = onCitySelect,
        getCitiesFlowUseCase = get(),
        getCitiesByCountryFlowUseCase = get(),
        updateCityUseCase = get(),
    )

fun ComponentFactory.createCountriesComponent(
    componentContext: ComponentContext,
    countryClickHandler: (String) -> Unit
): CountriesComponent =
    CountriesComponentImpl(
        componentContext = componentContext,
        getCountriesUseCase = get(),
        countryClickHandler = countryClickHandler,
    )

fun ComponentFactory.createDetailComponent(
    componentContext: ComponentContext,
    cityId: Long?,
): DetailComponent =
    DetailComponentImpl(
        componentContext = componentContext,
        cityId = cityId,
        updateWeatherUseCase = get(),
        getWeatherUseCase = get(),
        getCurrentLocationUseCase = get(),
        getCityByIdUseCase = get(),
    )

fun ComponentFactory.createFavoriteComponent(
    componentContext: ComponentContext,
    selectCityHandler: (City) -> Unit,
): FavoriteComponent =
    FavoriteComponentImpl(
        componentContext = componentContext,
        getFavoriteCitiesUseCase = get(),
        updateCityUseCase = get(),
        selectCityHandler = selectCityHandler,
    )

fun ComponentFactory.createOnboardingComponent(
    componentContext: ComponentContext,
    locationAvailableHandler: () -> Unit,
    goNextHandler: () -> Unit,
): OnboardingComponent =
    OnboardingComponentImpl(
        componentContext = componentContext,
        setOnboardingPassedUseCase = get(),
        locationAvailableHandler = locationAvailableHandler,
        goNextHandler = goNextHandler,
    )