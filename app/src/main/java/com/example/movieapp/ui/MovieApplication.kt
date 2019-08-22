package com.example.movieapp.ui

import android.app.Application
import com.example.movieapp.data.GlideImageLoader

class MovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        imageLoader = GlideImageLoader(this)
    }

    companion object {
        lateinit var imageLoader: GlideImageLoader
    }
}