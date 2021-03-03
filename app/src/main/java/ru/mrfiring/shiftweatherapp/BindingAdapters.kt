package ru.mrfiring.shiftweatherapp

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton
import ru.mrfiring.shiftweatherapp.presentation.detail.ApiStatus
import ru.mrfiring.shiftweatherapp.repository.network.FLAG_URL
import ru.mrfiring.shiftweatherapp.repository.network.IMG_URL

@BindingAdapter("loadState")
fun MaterialButton.bindLoadState(loadState: LoadState?){
    loadState?.let {
        visibility = when(loadState){
            is LoadState.Error -> {
                View.VISIBLE
            }
            else -> {
                View.GONE
            }
        }
    }
}

@BindingAdapter("loadState")
fun TextView.bindLoadState(loadState: LoadState?){
    loadState?.let {
        visibility = when(loadState){
            is LoadState.Error -> {
                View.VISIBLE
            }
            else -> {
                View.GONE
            }
        }
    }
}

@BindingAdapter("loadState")
fun ProgressBar.bindLoadState(loadState: LoadState?){
    loadState?.let {
        visibility = when(loadState){
            is LoadState.Loading -> {
                View.VISIBLE
            }
            else -> {
                View.GONE
            }
        }
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
            scaleX /= 2
            scaleY /= 2
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