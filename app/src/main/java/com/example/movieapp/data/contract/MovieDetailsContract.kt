package com.example.movieapp.data.contract

import com.example.movieapp.data.presenter.BasePresenter
import com.example.movieapp.data.view.BaseView
import com.example.movieapp.data.view.model.ViewMovie

interface MovieDetailsContract {

    interface Presenter : BasePresenter<View> {

        fun getMovieDetails(movieId: Int)

        fun getGenres(movie: ViewMovie)
    }

    interface View : BaseView {

        fun showMovieDetails(movie: ViewMovie)

        fun showGenres(genres: List<String>)

        fun showErrorMessage()

        fun onGenresError()
    }
}