package ru.mrfiring.shiftweatherapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import ru.mrfiring.shiftweatherapp.databinding.DetailFragmentBinding

enum class ApiStatus { LOADING, ERROR, DONE}

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DetailFragmentBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val argument = DetailFragmentArgs.fromBundle(requireArguments())
        val viewModel: DetailViewModel = getViewModel { parametersOf(argument.cityId)}

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }


}