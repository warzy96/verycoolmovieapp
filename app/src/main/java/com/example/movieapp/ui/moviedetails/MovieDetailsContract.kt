package com.example.movieapp.ui.moviedetails

import com.example.movieapp.ui.presenter.LifecycleAwarePresenter
import com.example.movieapp.ui.view.BaseView
import com.example.movieapp.ui.moviedetails.view.MovieDetailsViewModel

interface MovieDetailsContract {

    interface Presenter : LifecycleAwarePresenter {

        fun getMovieDetails(movieId: Int)
    }

    interface View : BaseView {

        fun showMovieDetails(movie: MovieDetailsViewModel)

        fun showErrorMessage(t: Throwable)
    }
}