package com.example.movieapp.ui.favorites

import com.example.movieapp.ui.presenter.LifecycleAwarePresenter
import com.example.movieapp.ui.view.BaseView
import com.example.movieapp.ui.view.model.MovieViewModel

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