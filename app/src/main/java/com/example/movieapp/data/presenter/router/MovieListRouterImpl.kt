package com.example.movieapp.data.presenter.router

import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.ui.fragments.FavoritesFragment
import com.example.movieapp.ui.fragments.MovieDetailsFragment
import com.example.movieapp.ui.fragments.MoviesFragment

class MovieListRouterImpl : MovieListRouter {

    override fun goBack(activity: AppCompatActivity) {
        if (activity.supportFragmentManager.backStackEntryCount > 1) {
            activity.supportFragmentManager.popBackStack()
        } else {
            activity.finish()
        }
    }

    override fun openMovieDetails(activity: AppCompatActivity, movieId: Int) {
        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom)
            .replace(R.id.fragment, MovieDetailsFragment.newInstance(movieId))
            .addToBackStack(MoviesFragment.TAG).commit()
    }

    override fun openPopularMovies(activity: AppCompatActivity) {
        val moviesFragment = activity.supportFragmentManager.findFragmentByTag(MoviesFragment.TAG + MovieApi.POPULARITY_SORT_NUM)
            ?: MoviesFragment(MovieApi.POPULARITY_SORT_NUM)

        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.fragment, moviesFragment, MoviesFragment.TAG + MovieApi.POPULARITY_SORT_NUM)
            .commit()
    }

    override fun openBestRatedMovies(activity: AppCompatActivity) {
        val moviesFragment = activity.supportFragmentManager.findFragmentByTag(MoviesFragment.TAG + MovieApi.VOTE_AVERAGE_SORT_NUM)
            ?: MoviesFragment(MovieApi.VOTE_AVERAGE_SORT_NUM)

        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.fragment, moviesFragment, MoviesFragment.TAG + MovieApi.VOTE_AVERAGE_SORT_NUM)
            .commit()
    }

    override fun openFavorites(activity: AppCompatActivity) {
        val favoritesFragment = activity.supportFragmentManager.findFragmentByTag(FavoritesFragment.TAG) ?: FavoritesFragment.newInstance()

        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.fragment, favoritesFragment, FavoritesFragment.TAG)
            .commit()
    }
}