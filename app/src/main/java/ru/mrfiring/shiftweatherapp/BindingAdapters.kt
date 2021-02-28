package ru.mrfiring.shiftweatherapp

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mrfiring.shiftweatherapp.home.HomeRecyclerViewAdapter
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity

@BindingAdapter("listData")
fun RecyclerView.bindData(items:  List<DomainCity>){
    val adpt = adapter as HomeRecyclerViewAdapter
    adpt.submitList(items)
}