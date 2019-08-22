package com.example.movieapp.data

import android.widget.ImageView
import com.bumptech.glide.RequestManager

class ImageLoaderImpl(private val requestManager: RequestManager) : ImageLoader {

    override fun loadPoster(path: String, imageView: ImageView) {
        loadImage(ImageLoader.POSTER_API_URL + path, imageView)
    }

    override fun loadImage(path: String, imageView: ImageView) {
        requestManager.load(path).into(imageView)
    }
}