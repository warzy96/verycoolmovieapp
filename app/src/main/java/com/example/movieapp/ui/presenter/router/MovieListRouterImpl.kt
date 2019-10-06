package com.example.movieapp.ui.presenter.router

import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.Fade
import android.transition.TransitionSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.ui.favorites.fragments.FavoritesFragment
import com.example.movieapp.ui.moviedetails.fragments.MovieDetailsFragment
import com.example.movieapp.ui.movies.fragments.MoviesFragment

class MovieListRouterImpl : MovieListRouter {

    private lateinit var activity: AppCompatActivity

    override fun setActivity(activity: AppCompatActivity) {
        this.activity = activity
    }

    override fun goBack() {
        if (activity.supportFragmentManager.backStackEntryCount > 1) {
            activity.supportFragmentManager.popBackStack()
        } else {
            activity.finish()
        }
    }

    override fun openMovieDetails(movieId: Int, sharedElement: View, transitionName: String) {
        val details = MovieDetailsFragment.newInstance(movieId)
        details.sharedElementEnterTransition = DetailsTransition()
        details.returnTransition = Fade()
        details.enterTransition = Fade()
        details.sharedElementReturnTransition = DetailsTransition()

        activity.supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addSharedElement(sharedElement, transitionName)
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.fragment, details)
            .addToBackStack(MoviesFragment.TAG).commit()
    }

    override fun openMovieDetails(movieId: Int) {
        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.fragment, MovieDetailsFragment.newInstance(movieId))
            .addToBackStack(MoviesFragment.TAG).commit()
    }

    override fun openPopularMovies() {
        val moviesFragment = activity.supportFragmentManager.findFragmentByTag(MoviesFragment.TAG + MovieApi.POPULARITY_SORT_NUM)
            ?: MoviesFragment(MovieApi.POPULARITY_SORT_NUM)

        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.fragment, moviesFragment, MoviesFragment.TAG + MovieApi.POPULARITY_SORT_NUM)
            .commit()
    }

    override fun openBestRatedMovies() {
        val moviesFragment = activity.supportFragmentManager.findFragmentByTag(MoviesFragment.TAG + MovieApi.VOTE_AVERAGE_SORT_NUM)
            ?: MoviesFragment(MovieApi.VOTE_AVERAGE_SORT_NUM)

        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.fragment, moviesFragment, MoviesFragment.TAG + MovieApi.VOTE_AVERAGE_SORT_NUM)
            .commit()
    }

    override fun openFavorites() {
        val favoritesFragment = activity.supportFragmentManager.findFragmentByTag(FavoritesFragment.TAG) ?: FavoritesFragment.newInstance()

        activity.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.fragment, favoritesFragment, FavoritesFragment.TAG)
            .commit()
    }
}

class DetailsTransition : TransitionSet() {
    companion object {
        private const val TRANSITION_DURATION = 500L
    }

    init {
        addTransition(ChangeBounds())
        addTransition(ChangeTransform())
        duration = TRANSITION_DURATION
        ordering = ORDERING_TOGETHER
    }
}