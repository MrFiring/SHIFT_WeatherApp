package ru.mrfiring.shiftweatherapp.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DetailFragmentBinding.inflate(inflater)
        val application = requireNotNull(activity).application
        val viewModelFactory = DetailViewModelFactory(application)

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }


}