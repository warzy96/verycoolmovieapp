package com.example.movieapp.data.contract

import com.example.movieapp.data.presenter.BasePresenter
import com.example.movieapp.data.view.BaseView
import com.example.movieapp.data.view.model.MovieViewModel

interface MovieListContract {

    abstract class Presenter : BasePresenter<View>() {

        abstract fun getMovies(query: String)

        abstract fun getNextPage(query: String)
    }

    interface View : BaseView {

        fun showMovies(movies: List<MovieViewModel>)

        fun showNextPage(movies: List<MovieViewModel>)

        fun showErrorMessage(t: Throwable)
    }
}