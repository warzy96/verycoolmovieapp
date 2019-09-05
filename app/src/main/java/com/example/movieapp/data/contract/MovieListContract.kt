package com.example.movieapp.data.contract

import android.content.Context
import com.example.movieapp.data.presenter.LifecycleAwarePresenter
import com.example.movieapp.data.view.BaseView
import com.example.movieapp.data.view.model.MovieViewModel

interface MovieListContract {

    interface Presenter : LifecycleAwarePresenter {

        fun getMovies(query: String)

        fun getNextPage(query: String)

        fun openMovieDetails(context: Context, movieId: Int)
    }

    interface View : BaseView {

        fun showMovies(movies: List<MovieViewModel>)

        fun showNextPage(movies: List<MovieViewModel>)

        fun showErrorMessage(t: Throwable)
    }
}