package com.example.movieapp.ui.presenter.router

import android.view.View
import androidx.appcompat.app.AppCompatActivity

interface MovieListRouter {

    fun setActivity(activity: AppCompatActivity)

    fun goBack()

    fun openMovieDetails(movieId: Int)

    fun openMovieDetails(movieId: Int, sharedElement: View, transitionName: String)

    fun openPopularMovies()

    fun openBestRatedMovies()

    fun openFavorites()
}