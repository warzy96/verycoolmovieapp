package com.example.movieapp.data.presenter.router

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movieapp.R
import com.example.movieapp.ui.fragments.MovieDetailsFragment

class MovieListRouterImpl : MovieListRouter {

    override fun goBack(activity: AppCompatActivity) {
        if (activity.supportFragmentManager.backStackEntryCount > 1) {
            activity.supportFragmentManager.popBackStack()
        } else {
            activity.finish()
        }
    }

    override fun openMovieDetails(activity: AppCompatActivity, currentFragment: Fragment, movieId: Int) {
        activity.supportFragmentManager
            .beginTransaction()
            .hide(currentFragment)
            .add(R.id.fragment, MovieDetailsFragment.newInstance(movieId))
            .addToBackStack(null).commit()
    }
}