package com.example.movieapp.data.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.RequestListener

interface ImageLoader {

    fun loadImage(path: String, imageView: ImageView)

    fun loadImage(path: String, imageView: ImageView, listener: RequestListener<Drawable>)
}