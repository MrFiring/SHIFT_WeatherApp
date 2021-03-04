package ru.mrfiring.shiftweatherapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import ru.mrfiring.shiftweatherapp.presentation.ShowLoading
import ru.mrfiring.shiftweatherapp.presentation.ShowNetworkError
import ru.mrfiring.shiftweatherapp.presentation.composables.detail.MainParametersAnimatedCard
import ru.mrfiring.shiftweatherapp.presentation.composables.detail.WeatherCard

enum class ApiStatus { LOADING, ERROR, DONE}

@ExperimentalAnimationApi
class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val argument = DetailFragmentArgs.fromBundle(requireArguments())

        val viewModel: DetailViewModel = getViewModel { parametersOf(argument.cityId)}
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
fun Details(viewModel: DetailViewModel) {
    val params by viewModel.container.observeAsState()
    val status by viewModel.status.observeAsState()

    when (status) {
        ApiStatus.LOADING -> {
            ShowLoading()
        }
        ApiStatus.DONE -> {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherCard(params!!.weather)
                MainParametersAnimatedCard(params!!.mainParams)
            }
        }
        else -> {
            ShowNetworkError(viewModel::onRetryPressed)
        }

    }
}

