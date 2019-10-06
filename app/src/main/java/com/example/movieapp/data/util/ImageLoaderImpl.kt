package com.example.movieapp.data.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.example.movieapp.R

class ImageLoaderImpl(private val requestManager: RequestManager) : ImageLoader {

    override fun loadImage(path: String, imageView: ImageView) {
        requestManager.load(path)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(R.drawable.movie_default_foreground)
            .into(imageView)
    }

    override fun loadImage(path: String, imageView: ImageView, listener: RequestListener<Drawable>) {
        requestManager.load(path)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .addListener(listener)
            .error(R.drawable.movie_default_foreground)
            .into(imageView)
    }
}