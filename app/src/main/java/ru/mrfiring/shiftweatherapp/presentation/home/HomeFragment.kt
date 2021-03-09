package ru.mrfiring.shiftweatherapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.mrfiring.shiftweatherapp.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    @ExperimentalPagingApi
    private val viewModel: HomeViewModel by sharedViewModel<HomeViewModel>()

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HomeFragmentBinding.inflate(layoutInflater)
        val adapter = HomeRecyclerViewAdapter(
                HomeRecyclerViewAdapter.ClickListener {
                    viewModel.onCityClicked(it)
                }
        )

        val loadStateAdapter = HomeLoadStateAdapter(HomeLoadStateAdapter.OnRetryListener {
            adapter.retry()
        })

        adapter.withLoadStateFooter(loadStateAdapter)

        adapter.addLoadStateListener{
            viewModel.onLoadStateEvent(it.refresh)
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.citiesList.adapter = adapter


        viewModel.cities.observe(viewLifecycleOwner, Observer {
            adapter.submitData(lifecycle, it)
        })

        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer{
            it?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id)
                )
            }
        })


        return binding.root
    }



}