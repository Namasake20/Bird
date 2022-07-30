package com.namasake.animal.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.namasake.animal.databinding.BirdItemBinding
import com.namasake.animal.domain.model.Bird

class BirdAdapter:ListAdapter<Bird,BirdAdapter.BirdViewHolder>(BirdComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirdViewHolder {
        val binding = BirdItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BirdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BirdViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null){
            holder.bind(currentItem)

        }
    }

    class BirdViewHolder(private val binding: BirdItemBinding):
            RecyclerView.ViewHolder(binding.root){
        fun bind(bird: Bird){
            binding.apply {
                tvName.text = bird.name
                tvAge.text = bird.age

            }
        }
    }

    class BirdComparator: DiffUtil.ItemCallback<Bird>(){
        override fun areItemsTheSame(oldItem: Bird, newItem: Bird) = oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Bird, newItem: Bird): Boolean =
                oldItem == newItem

    }
}