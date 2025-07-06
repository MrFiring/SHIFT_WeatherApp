package ru.mrfiring.shiftweatherapp.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import ru.mrfiring.shiftweatherapp.di.ComponentFactory
import ru.mrfiring.shiftweatherapp.di.createCitiesComponent
import ru.mrfiring.shiftweatherapp.di.createCountriesComponent
import ru.mrfiring.shiftweatherapp.di.createDetailComponent
import ru.mrfiring.shiftweatherapp.di.createFavoriteComponent
import ru.mrfiring.shiftweatherapp.presentation.cities.CitiesComponent
import ru.mrfiring.shiftweatherapp.presentation.countries.CountriesComponent
import ru.mrfiring.shiftweatherapp.presentation.detail.DetailComponent
import ru.mrfiring.shiftweatherapp.presentation.favorite.FavoriteComponent
import ru.mrfiring.shiftweatherapp.presentation.main.MainComponentImpl.Child

interface MainComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onChangeThemeClick()

    fun onCitiesClick()
    fun onCountriesClick()
    fun onFavoritesClick()
}

class MainComponentImpl(
    componentContext: ComponentContext,
    private val locationGranted: Boolean,
    private val changeThemeHandler: () -> Unit,
    private val componentFactory: ComponentFactory,
) : MainComponent, ComponentContext by componentContext {

    private val navigation: StackNavigation<Config> = StackNavigation()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = getInitialConfiguration(),
        handleBackButton = true,
        childFactory = ::child
    )

    private fun getInitialConfiguration(): Config =
        when {
            !locationGranted -> Config.Cities(country = null)
            else -> Config.Detail(cityId = null)
        }

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Cities -> Child.Cities(
                componentFactory.createCitiesComponent(
                    componentContext,
                    country = config.country.orEmpty(),
                    onCitySelect = { city -> navigation.push(Config.Detail(cityId = city.id)) }
                )
            )

            Config.Countries -> Child.Countries(
                componentFactory.createCountriesComponent(
                    componentContext,
                    countryClickHandler = { countryCode -> navigation.push(Config.Cities(countryCode)) }
                )
            )

            is Config.Detail -> Child.Detail(
                componentFactory.createDetailComponent(
                    componentContext,
                    cityId = config.cityId
                )
            )

            Config.Favorite -> Child.Favorite(
                componentFactory.createFavoriteComponent(
                    componentContext,
                    selectCityHandler = { city -> navigation.push(Config.Detail(cityId = city.id)) }
                )
            )
        }

    override fun onCitiesClick() {
        navigation.replaceAll(Config.Cities(country = null))
    }

    override fun onCountriesClick() {
        navigation.replaceAll(Config.Countries)
    }

    override fun onFavoritesClick() {
        navigation.replaceAll(Config.Favorite)
    }

    override fun onChangeThemeClick() {
        changeThemeHandler()
    }

    sealed class Child {

        class Cities(val component: CitiesComponent) : Child()
        class Countries(val component: CountriesComponent) : Child()
        class Detail(val component: DetailComponent) : Child()
        class Favorite(val component: FavoriteComponent) : Child()
    }

    @Serializable
    sealed interface Config {

        @Serializable
        data class Cities(val country: String?) : Config

        @Serializable
        data object Countries : Config

        @Serializable
        data class Detail(val cityId: Long?) : Config

        @Serializable
        data object Favorite : Config
    }
}