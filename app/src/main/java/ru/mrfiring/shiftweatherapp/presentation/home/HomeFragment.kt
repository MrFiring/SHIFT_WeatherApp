package ru.mrfiring.shiftweatherapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.chrisbanes.accompanist.glide.GlideImage
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.mrfiring.shiftweatherapp.R
import ru.mrfiring.shiftweatherapp.data.network.FLAG_URL
import ru.mrfiring.shiftweatherapp.domain.DomainCity

class HomeFragment : Fragment() {

    @ExperimentalPagingApi
    private val viewModel: HomeViewModel by sharedViewModel<HomeViewModel>()

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val binding = HomeFragmentBinding.inflate(layoutInflater)
//        val adapter = HomeRecyclerViewAdapter(
//                HomeRecyclerViewAdapter.ClickListener {
//                    viewModel.onCityClicked(it)
//                }
//        )
//
//        val loadStateAdapter = HomeLoadStateAdapter(HomeLoadStateAdapter.OnRetryListener {
//            adapter.retry()
//        })

//        adapter.withLoadStateFooter(loadStateAdapter)

//        adapter.addLoadStateListener{
//            viewModel.onLoadStateEvent(it.refresh)
//
//        }
//
//        binding.viewModel = viewModel
//        binding.lifecycleOwner = this
//        binding.citiesList.adapter = adapter
//

//        viewModel.cities.observe(viewLifecycleOwner, Observer {
//            lifecycleScope.launch{
////                adapter.submitData(it)
//            }
//        })

//        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer{
//            it?.let {
//                this.findNavController().navigate(
//                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id)
//                )
//            }
//        })


//        return binding.root

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    HomeContent(viewModel)
                }
            }
        }
    }


}


@ExperimentalPagingApi
@Composable
fun HomeContent(homeViewModel: HomeViewModel) {
    val lazyPagingItems = homeViewModel.cities.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyPagingItems) {
            it?.let { city ->
                CityItem(domainCity = city) {}
            }
        }
    }
}

@Composable
fun CityItem(domainCity: DomainCity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val url = FLAG_URL.format(domainCity.country)
            GlideImage(
                data = url,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp, 64.dp)
                    .padding(8.dp, 8.dp, 4.dp, 8.dp),
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }
                },
                error = {
                    Image(
                        painter = painterResource(R.drawable.ic_connection_error),
                        contentDescription = null
                    )
                }
            )
            Text(text = domainCity.name, style = MaterialTheme.typography.h5)
            Text(
                text = domainCity.country, style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}