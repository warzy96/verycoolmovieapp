package com.example.movieapp.ui.presenter.router

import androidx.appcompat.app.AppCompatActivity

interface MovieListRouter {

    fun setActivity(activity: AppCompatActivity)

    fun goBack()

    fun openMovieDetails(movieId: Int)

    fun openPopularMovies()

    fun openBestRatedMovies()

    fun openFavorites()
}