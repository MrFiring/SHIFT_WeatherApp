package ru.mrfiring.shiftweatherapp.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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

        val adapter = HomeRecyclerViewAdapter(
                HomeRecyclerViewAdapter.ClickListener {
                    viewModel.onCityClicked(it)
                }
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.citiesList.adapter = adapter

        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer{
            it?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id)
                )
                viewModel.doneNavigating()
            }
        })


        return binding.root
    }


}