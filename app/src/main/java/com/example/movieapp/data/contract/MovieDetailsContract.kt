package com.example.movieapp.data.contract

import com.example.movieapp.data.presenter.BasePresenter
import com.example.movieapp.data.view.BaseView
import com.example.movieapp.data.view.model.MovieDetailsViewModel

interface MovieDetailsContract {

    abstract class Presenter : BasePresenter<View>() {

        abstract fun getMovieDetails(movieId: Int)
    }

    interface View : BaseView {

        fun showMovieDetails(movie: MovieDetailsViewModel)

        fun showErrorMessage(t: Throwable)
    }
}