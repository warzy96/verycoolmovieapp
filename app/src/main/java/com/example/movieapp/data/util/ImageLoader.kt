package com.example.movieapp.data.util

import android.widget.ImageView

interface ImageLoader {

    fun loadImage(path: String, imageView: ImageView)
}