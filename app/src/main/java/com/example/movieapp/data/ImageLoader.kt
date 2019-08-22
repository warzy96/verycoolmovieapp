package com.example.movieapp.data

import android.widget.ImageView

interface ImageLoader {

    companion object {
        const val POSTER_API_URL = "https://image.tmdb.org/t/p/original/"
    }

    fun loadPoster(path: String, imageView: ImageView)

    fun loadImage(path: String, imageView: ImageView)
}