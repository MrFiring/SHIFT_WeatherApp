package ru.mrfiring.shiftweatherapp.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mrfiring.shiftweatherapp.databinding.ItemLoaderBinding

class HomeLoadStateAdapter(
    private val retry: OnRetryListener
): LoadStateAdapter<HomeLoadStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: ItemLoaderBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState, onRetryListener: OnRetryListener){
            binding.loadState = loadState
            binding.onRetryListener = onRetryListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLoaderBinding.inflate(layoutInflater)
                return ViewHolder(binding)
            }
        }
    }

    class OnRetryListener(val retry: () -> Unit){
        fun onRetry() = retry()
    }

}