package com.example.movieapp.data

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader(private val context: Context): ImageLoader{

    override fun loadPoster(path: String, imageView: ImageView) {
        loadImage(ImageLoader.POSTER_API_URL + path, imageView)
    }

    override fun loadImage(path: String, imageView: ImageView) {
        Glide.with(context).load(path).into(imageView)
    }
}