package ru.mrfiring.shiftweatherapp.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import kotlinx.coroutines.launch
import ru.mrfiring.shiftweatherapp.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    @ExperimentalPagingApi
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

        viewModel.cities.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch{
                adapter.submitData(it)
            }
        })

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