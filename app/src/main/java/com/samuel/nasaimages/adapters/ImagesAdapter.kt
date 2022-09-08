package com.samuel.nasaimages.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.samuel.domain.models.Image
import com.samuel.nasaimages.databinding.ItemImageBinding


class ImagesAdapter(val imageListener: ImageListener) :
    PagingDataAdapter<Image, ImagesAdapter.CityViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.setImage(getItem(position))
    }

    inner class CityViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                imageListener.openImage(getItem(absoluteAdapterPosition))
            }
        }

        fun setImage(image: Image?) {
            binding.image = image
        }
    }
}

class Comparator : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }
}

interface ImageListener {

    fun openImage(image: Image?)
}

