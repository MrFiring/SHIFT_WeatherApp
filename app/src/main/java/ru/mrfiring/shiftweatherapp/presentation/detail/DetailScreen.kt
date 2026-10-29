package ru.mrfiring.shiftweatherapp.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Thunderstorm
import androidx.compose.material.icons.rounded.Water
import androidx.compose.material.icons.rounded.WbCloudy
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.models.City
import ru.mrfiring.shiftweatherapp.domain.models.MainWeatherParameters
import ru.mrfiring.shiftweatherapp.domain.models.Rain
import ru.mrfiring.shiftweatherapp.domain.models.Snow
import ru.mrfiring.shiftweatherapp.domain.models.WeatherContainer
import ru.mrfiring.shiftweatherapp.domain.models.Wind
import ru.mrfiring.shiftweatherapp.presentation.composables.LoadingIndicator
import ru.mrfiring.shiftweatherapp.presentation.composables.NetworkError
import ru.mrfiring.shiftweatherapp.presentation.theme.ShiftExtendedColors
import ru.mrfiring.shiftweatherapp.presentation.theme.extendedColors

private const val EPOCH_MILLIS_THRESHOLD = 100_000_000_000L
private const val MILLIS_PER_SECOND = 1_000L
private const val TIME_FORMAT_PATTERN = "HH:mm"

private val EXPANDED_TITLE_FONT_SIZE = 28.sp
private val COLLAPSED_TITLE_FONT_SIZE = 22.sp
private const val SUBTITLE_HIDE_FRACTION = 0.5f

// Диапазоны кодов погодных условий OpenWeather (https://openweathermap.org/weather-conditions).
private val THUNDERSTORM_CODES = 200..299
private val DRIZZLE_AND_RAIN_CODES = 300..599
private val SNOW_CODES = 600..699
private val ATMOSPHERE_CODES = 700..799
private const val CLEAR_SKY_CODE = 800
private const val FEW_CLOUDS_CODE = 801

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    detailComponent: DetailComponent,
    onMenuClick: () -> Unit,
    onCitiesClick: () -> Unit,
    onThemeToggleClick: () -> Unit,
) {
    val state by detailComponent.state.subscribeAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        LargeTopAppBar(
            title = {
                DetailAppBarTitle(
                    content = state as? DetailState.Content,
                    collapsedFraction = scrollBehavior.state.collapsedFraction
                )
            },
            navigationIcon = {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = onCitiesClick) {
                    Icon(
                        imageVector = Icons.Rounded.LocationCity,
                        contentDescription = null
                    )
                }

                IconButton(onClick = onThemeToggleClick) {
                    Icon(
                        imageVector = Icons.Rounded.LightMode,
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            scrollBehavior = scrollBehavior
        )

        Crossfade(targetState = state) { targetState ->
            when (targetState) {
                is DetailState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator()
                    }
                }

                is DetailState.Content -> {
                    DetailContent(container = targetState.container)
                }

                is DetailState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        NetworkError(
                            message = stringResource(R.string.error_weather_message),
                            onRetry = detailComponent::onRetryPressed
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailAppBarTitle(
    content: DetailState.Content?,
    collapsedFraction: Float
) {
    Column {
        Text(
            text = content?.city?.name
                ?: stringResource(R.string.detail_current_location),
            fontSize = lerp(
                start = EXPANDED_TITLE_FONT_SIZE,
                stop = COLLAPSED_TITLE_FONT_SIZE,
                fraction = collapsedFraction
            ),
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        AnimatedVisibility(
            visible = collapsedFraction < SUBTITLE_HIDE_FRACTION
        ) {
            if (content != null) {
                Column {
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = buildDetailSubtitle(
                            container = content.container,
                            city = content.city
                        ),
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun buildDetailSubtitle(
    container: WeatherContainer,
    city: City?
): String {
    val updatedTime = formatUpdateTime(container.lastUpdate)
    val subtitle = stringResource(R.string.detail_updated_at, updatedTime)

    val cityLocation = city?.let { buildCityLocationText(it) }

    return if (cityLocation == null) {
        subtitle
    } else {
        "$subtitle · $cityLocation"
    }
}

@Composable
private fun buildCityLocationText(city: City): String =
    if (city.state.isBlank()) {
        city.country
    } else {
        stringResource(R.string.detail_subtitle_location, city.state, city.country)
    }

private fun formatUpdateTime(lastUpdate: Long): String {
    val epochMillis = if (lastUpdate < EPOCH_MILLIS_THRESHOLD) {
        lastUpdate * MILLIS_PER_SECOND
    } else {
        lastUpdate
    }

    return SimpleDateFormat(TIME_FORMAT_PATTERN, Locale.getDefault()).format(Date(epochMillis))
}

@Composable
private fun DetailContent(
    container: WeatherContainer
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 18.dp, end = 18.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        CurrentWeatherBlock(
            weatherId = container.weather.weatherId,
            conditionTitle = container.weather.main,
            temp = container.mainParams.temp,
            feelsLike = container.mainParams.feelsLike
        )

        Spacer(modifier = Modifier.height(24.dp))

        WeatherTilesRow(
            wind = container.wind,
            mainParams = container.mainParams
        )

        val precipitationEntries = getPrecipitationEntries(
            rain = container.rain,
            snow = container.snow
        )

        if (precipitationEntries.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            PrecipitationBlock(entries = precipitationEntries)
        }
    }
}

@Composable
private fun CurrentWeatherBlock(
    weatherId: Int,
    conditionTitle: String,
    temp: Double,
    feelsLike: Double
) {
    val iconSpec = getWeatherIconSpec(
        weatherId = weatherId,
        colors = MaterialTheme.extendedColors
    )
    val iconTint = if (isSystemInDarkTheme()) {
        iconSpec.darkTint
    } else {
        iconSpec.lightTint
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = iconSpec.icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(44.dp)
        )

        Text(
            text = stringResource(R.string.temp_format, temp),
            fontSize = 72.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = conditionTitle.replaceFirstChar { it.uppercase(Locale.US) },
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = stringResource(R.string.temp_feels_format, feelsLike),
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private data class WeatherIconSpec(
    val icon: ImageVector,
    val lightTint: Color,
    val darkTint: Color
)

private fun getWeatherIconSpec(
    weatherId: Int,
    colors: ShiftExtendedColors
): WeatherIconSpec = when {
    weatherId in THUNDERSTORM_CODES -> WeatherIconSpec(
        icon = Icons.Rounded.Thunderstorm,
        lightTint = colors.stormInk,
        darkTint = colors.storm
    )

    weatherId in DRIZZLE_AND_RAIN_CODES -> WeatherIconSpec(
        icon = Icons.Rounded.Water,
        lightTint = colors.rainInk,
        darkTint = colors.rain
    )

    weatherId in SNOW_CODES -> WeatherIconSpec(
        icon = Icons.Rounded.AcUnit,
        lightTint = colors.snowInk,
        darkTint = colors.snow
    )

    weatherId in ATMOSPHERE_CODES -> WeatherIconSpec(
        icon = Icons.Rounded.Air,
        lightTint = colors.fogInk,
        darkTint = colors.fog
    )

    weatherId == CLEAR_SKY_CODE -> WeatherIconSpec(
        icon = Icons.Rounded.WbSunny,
        lightTint = colors.clearInk,
        darkTint = colors.clear
    )

    weatherId == FEW_CLOUDS_CODE -> WeatherIconSpec(
        icon = Icons.Rounded.WbCloudy,
        lightTint = colors.partlyInk,
        darkTint = colors.partly
    )

    else -> WeatherIconSpec(
        icon = Icons.Rounded.Cloud,
        lightTint = colors.cloudyInk,
        darkTint = colors.cloudy
    )
}

@Composable
private fun WeatherTilesRow(
    wind: Wind,
    mainParams: MainWeatherParameters
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WeatherTile(
            tileKey = stringResource(R.string.detail_wind),
            tileValue = stringResource(
                R.string.wind_value,
                wind.speed,
                wind.degToWord()
            ),
            modifier = Modifier.weight(1f)
        )

        WeatherTile(
            tileKey = stringResource(R.string.detail_humidity),
            tileValue = stringResource(R.string.humidity_value, mainParams.humidity),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun WeatherTile(
    tileKey: String,
    tileValue: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.extendedColors.surfaceContainer
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text(
                text = tileKey.uppercase(Locale.US),
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.08.em),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = tileValue,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

private data class PrecipitationEntry(
    val labelRes: Int,
    val millimeters: Double
)

private fun getPrecipitationEntries(rain: Rain?, snow: Snow?): List<PrecipitationEntry> {
    val entries = mutableListOf<PrecipitationEntry>()

    if (rain != null) {
        if (rain.forLastOneHour > 0) {
            entries.add(PrecipitationEntry(R.string.precipitation_rain_last_hour, rain.forLastOneHour))
        }
        if (rain.forLastThreeHours > 0) {
            entries.add(PrecipitationEntry(R.string.precipitation_rain_last_three_hours, rain.forLastThreeHours))
        }
    }

    if (snow != null) {
        if (snow.forLastOneHour > 0) {
            entries.add(PrecipitationEntry(R.string.precipitation_snow_last_hour, snow.forLastOneHour))
        }
        if (snow.forLastThreeHours > 0) {
            entries.add(PrecipitationEntry(R.string.precipitation_snow_last_three_hours, snow.forLastThreeHours))
        }
    }

    return entries
}

@Composable
private fun PrecipitationBlock(
    entries: List<PrecipitationEntry>
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.extendedColors.surfaceContainer
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text(
                text = stringResource(R.string.detail_precipitation).uppercase(Locale.US),
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.08.em),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            entries.forEach { entry ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(entry.labelRes),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = stringResource(R.string.precipitation_value, entry.millimeters),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}
