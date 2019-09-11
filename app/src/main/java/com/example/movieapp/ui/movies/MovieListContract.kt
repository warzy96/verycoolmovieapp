package com.example.movieapp.ui.movies

import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.ui.presenter.LifecycleAwarePresenter
import com.example.movieapp.ui.view.BaseView
import com.example.movieapp.ui.view.model.MovieViewModel

interface MovieListContract {

    interface Presenter : LifecycleAwarePresenter {

        fun setActivity(activity: AppCompatActivity)

        fun goBack()

        fun openMovieDetails(movieId: Int)

        fun openPopularMovies()

        fun openBestRatedMovies()

        fun openFavorites()

        fun getMovies(query: String, sort: String)

        fun getNextPage(query: String, sort: String)

        fun saveFavorite(movie: MovieViewModel)

        fun removeFavorite(movie: MovieViewModel)
    }

    interface View : BaseView {

        fun showMovies(movies: List<MovieViewModel>)

        fun showNextPage(movies: List<MovieViewModel>)

        fun showErrorMessage(t: Throwable)
    }
}