package com.example.movieapp.data.presenter.router

import android.content.Context

interface MovieListRouter {

    fun openMovieDetails(context: Context, movieId: Int)
}