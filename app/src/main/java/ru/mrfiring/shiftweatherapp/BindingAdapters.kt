package ru.mrfiring.shiftweatherapp

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mrfiring.shiftweatherapp.home.ApiStatus
import ru.mrfiring.shiftweatherapp.home.HomeRecyclerViewAdapter
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity

@BindingAdapter("listData")
fun RecyclerView.bindData(items: List<DomainCity>?){
    items?.let {
        val adpt = adapter as HomeRecyclerViewAdapter
        adpt.submitList(items)
    }
}

@BindingAdapter("apiStatus")
fun ImageView.bindStatus(status: ApiStatus?){
    when(status){
        ApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.DONE -> {
            visibility = View.GONE
        }
        ApiStatus.ERROR ->{
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_connection_error)
        }
    }
}