package com.example.movieapp.data.callback

import android.graphics.Bitmap

interface PosterCallback {

    fun onPosterFetched(bitmap: Bitmap)
}