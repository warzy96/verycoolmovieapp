package com.example.movieapp.data

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoaderImpl(private val glide: Glide) : ImageLoader {

    override fun loadImage(path: String, imageView: ImageView) {
        Glide.with(glide.context).load(path).into(imageView)
    }
}