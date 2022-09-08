package com.samuel.nasaimages.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.samuel.nasaimages.R

class BindingAdapter {
    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImage(view: ImageView, url: String) {
            Glide.with(view)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(view)
        }

        @BindingAdapter("url")
        @JvmStatic
        fun setCoverImage(view: ImageView, url: String) {
            Glide.with(view)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.loading)
                .into(view)
        }
    }
}