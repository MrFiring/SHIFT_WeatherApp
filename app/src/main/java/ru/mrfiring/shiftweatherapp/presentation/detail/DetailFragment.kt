package ru.mrfiring.shiftweatherapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.glide.GlideImage
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import ru.mrfiring.shiftweatherapp.data.network.IMG_URL
import ru.mrfiring.shiftweatherapp.domain.DomainMainWeatherParameters
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.domain.DomainWeather

enum class ApiStatus { LOADING, ERROR, DONE}

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argument = DetailFragmentArgs.fromBundle(requireArguments())
//        val viewModel: DetailViewModel by sharedViewModel { parametersOf(argument.cityId)}
        val viewModel: DetailViewModel = getSharedViewModel { parametersOf(argument.cityId)}
        viewModel.refreshWeather()
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                   Details(viewModel = viewModel)
                }
            }
        }
    }


}


@Composable
fun WeatherCard(domainWeather: DomainWeather) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val url = IMG_URL.format(domainWeather.icon)
            GlideImage(
                data = url,
                contentDescription = "Weather image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(0.dp, 8.dp)
            )

            Text(
                text = domainWeather.main,
                style = MaterialTheme.typography.h5
            )

            Text(
                text = domainWeather.description,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.caption
            )

        }
    }
}

@Composable
fun MainParametersCard(domainMainWeatherParameters: DomainMainWeatherParameters) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val curTemp = stringResource(id = R.string.temp_format, domainMainWeatherParameters.temp)
                val feelsLike = stringResource(id = R.string.temp_feels_format, domainMainWeatherParameters.feelsLike)
                val minTemp = stringResource(id = R.string.temp_format, domainMainWeatherParameters.tempMin)
                val maxTemp = stringResource(id = R.string.temp_format, domainMainWeatherParameters.tempMax)
                val humidity = stringResource(id = R.string.humidity_format, domainMainWeatherParameters.humidity)
                val pressure = stringResource(id = R.string.pressure_format, domainMainWeatherParameters.pressure)
                Text(
                    text = curTemp,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = feelsLike,
                    modifier = Modifier.padding(),
                    style = MaterialTheme.typography.h6
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Min: $minTemp",
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Blue
                        )
                        Text(
                            text = "Max: $maxTemp",
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.Red
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = humidity,
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.subtitle1,

                            )
                        Text(
                            text = pressure,
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.subtitle1,

                            )
                    }
                }
            }

    }
}

@Composable
fun Details(viewModel: DetailViewModel){
    val params by viewModel.container.observeAsState()

    if(params != null) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            WeatherCard(params!!.weather)
            MainParametersCard(params!!.mainParams)
        }
    }else {
        Text(text = "HELLO WORLD", style = MaterialTheme.typography.h3)
    }

}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun PreviewShow(){
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        WeatherCard()
//        MainParametersCard()
//    }
//
//}

