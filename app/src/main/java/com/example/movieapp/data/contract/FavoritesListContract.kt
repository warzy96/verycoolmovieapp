package com.example.movieapp.data.contract

import com.example.movieapp.data.presenter.LifecycleAwarePresenter
import com.example.movieapp.data.view.BaseView
import com.example.movieapp.data.view.model.MovieViewModel

interface FavoritesListContract {

    interface Presenter : LifecycleAwarePresenter {

        fun saveFavorite(movie: MovieViewModel)

        fun removeFavorite(movie: MovieViewModel)

        fun getFavorites()
    }

    interface View : BaseView {

        fun showMovies(movies: List<MovieViewModel>)

        fun showErrorMessage(t: Throwable)
    }
}