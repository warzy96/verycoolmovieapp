package com.example.movieapp.data.util

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.example.movieapp.R

class ImageLoaderImpl(private val requestManager: RequestManager) : ImageLoader {

    override fun loadImage(path: String, imageView: ImageView) {
        requestManager.load(path).error(R.drawable.movie_default_foreground).into(imageView)
    }
}