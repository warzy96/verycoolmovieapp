package com.example.movieapp.data.presenter.router

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface MovieListRouter {

    fun goBack(activity: AppCompatActivity)

    fun openMovieDetails(activity: AppCompatActivity, currentFragment: Fragment, movieId: Int)
}