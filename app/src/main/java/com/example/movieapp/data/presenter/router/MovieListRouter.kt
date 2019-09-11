package com.example.movieapp.data.presenter.router

import androidx.appcompat.app.AppCompatActivity

interface MovieListRouter {

    fun goBack(activity: AppCompatActivity)

    fun openMovieDetails(activity: AppCompatActivity, movieId: Int)

    fun openPopularMovies(activity: AppCompatActivity)

    fun openBestRatedMovies(activity: AppCompatActivity)

    fun openFavorites(activity: AppCompatActivity)
}