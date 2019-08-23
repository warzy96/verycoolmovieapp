package com.example.movieapp.ui

import android.app.Application
import com.bumptech.glide.Glide
import com.example.movieapp.data.ImageLoaderImpl

class MovieApplication : Application() {

    companion object {
        lateinit var imageLoader: ImageLoaderImpl
    }

    override fun onCreate() {
        super.onCreate()
        imageLoader = ImageLoaderImpl(Glide.get(this))
    }
}