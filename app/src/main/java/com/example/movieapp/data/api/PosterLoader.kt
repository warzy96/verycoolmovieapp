package com.example.movieapp.data.api

import android.widget.ImageView
import com.bumptech.glide.RequestManager

class PosterLoader {

    companion object {
        const val POSTER_API_URL = "https://image.tmdb.org/t/p/original/"

        fun loadImage(requestManager: RequestManager, path: String, imageView: ImageView) = requestManager.load(POSTER_API_URL + path).into(imageView)
    }
}