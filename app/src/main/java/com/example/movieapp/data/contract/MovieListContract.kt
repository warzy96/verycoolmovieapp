package com.example.movieapp.data.contract

import com.example.movieapp.data.presenter.LifecycleAwarePresenter
import com.example.movieapp.data.view.BaseView
import com.example.movieapp.data.view.model.MovieViewModel

interface MovieListContract {

    interface Presenter : LifecycleAwarePresenter {

        fun getMovies(query: String)

        fun getNextPage(query: String)

        fun saveFavorite(movie: MovieViewModel)

        fun removeFavorite(movie: MovieViewModel)
    }

    interface View : BaseView {

        fun showMovies(movies: List<MovieViewModel>)

        fun showNextPage(movies: List<MovieViewModel>)

        fun showErrorMessage(t: Throwable)
    }
}