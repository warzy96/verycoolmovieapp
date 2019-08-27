package com.example.movieapp.data.contract

import com.example.movieapp.data.presenter.BasePresenter
import com.example.movieapp.data.view.BaseView
import com.example.movieapp.data.view.model.MovieViewModel

interface MovieListContract {

    interface Presenter : BasePresenter<View> {

        fun getMovies()
    }

    interface View : BaseView {

        fun showMovies(movies: List<MovieViewModel>)

        fun showErrorMessage(t: Throwable)
    }
}