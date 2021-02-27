package ru.mrfiring.shiftweatherapp.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HomeFragmentBinding.inflate(layoutInflater)
        val application = requireNotNull(activity).application
        val viewModelFactory = HomeViewModelFactory(application)

        val viewModel: HomeViewModel = ViewModelProvider(this,
            viewModelFactory).get(HomeViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        return binding.root
    }


}