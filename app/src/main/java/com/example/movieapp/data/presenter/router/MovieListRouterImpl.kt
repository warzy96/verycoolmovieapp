package com.example.movieapp.data.presenter.router

import android.content.Context
import com.example.movieapp.ui.activities.MovieDetailsActivity

class MovieListRouterImpl: MovieListRouter {

    override fun openMovieDetails(context: Context, movieId: Int) {
        context.startActivity(MovieDetailsActivity.createIntent(context, movieId))
    }
}