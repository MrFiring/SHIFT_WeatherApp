package ru.mrfiring.shiftweatherapp.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mrfiring.shiftweatherapp.databinding.ListItemViewBinding
import ru.mrfiring.shiftweatherapp.repository.domain.DomainCity

class HomeRecyclerViewAdapter(
    private val clickListener: ClickListener
) : ListAdapter<DomainCity, HomeRecyclerViewAdapter.CityViewHolder>(CityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class CityViewHolder(
            private val binding: ListItemViewBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: DomainCity, clickListener: ClickListener){
            binding.city = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): CityViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemViewBinding.inflate(layoutInflater, parent, false)

                return CityViewHolder(binding)
            }
        }

    }

    class CityDiffCallback : DiffUtil.ItemCallback<DomainCity>(){
        override fun areItemsTheSame(oldItem: DomainCity, newItem: DomainCity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainCity, newItem: DomainCity): Boolean {
            return oldItem == newItem
        }
    }

    class ClickListener(val block: (item: DomainCity) -> Unit){
        fun onClick(item: DomainCity) = block(item)
    }
}