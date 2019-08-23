package com.example.movieapp.data

import android.widget.ImageView

interface ImageLoader {

    fun loadImage(path: String, imageView: ImageView)
}