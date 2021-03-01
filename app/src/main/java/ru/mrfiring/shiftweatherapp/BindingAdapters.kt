package ru.mrfiring.shiftweatherapp

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.mrfiring.shiftweatherapp.home.ApiStatus
import ru.mrfiring.shiftweatherapp.home.HomeRecyclerViewAdapter
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity
import ru.mrfiring.shiftweatherapp.repository.network.FLAG_URL
import ru.mrfiring.shiftweatherapp.repository.network.IMG_URL

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

@BindingAdapter("imgCode")
fun ImageView.loadImage(code : String?){
    code?.let {
        val url = IMG_URL.format(code)
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_connection_error)
                )
                .into(this)
    }
}

@BindingAdapter("loadFlagFor")
fun ImageView.loadFlagImage(country: String?){
    country?.let{
        val url = FLAG_URL.format(country)
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_connection_error)
            )
            .into(this)
    }
}