package com.example.movieapp.data.contract

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movieapp.data.presenter.LifecycleAwarePresenter
import com.example.movieapp.data.view.BaseView
import com.example.movieapp.data.view.model.MovieViewModel

interface MovieListContract {

    interface Presenter : LifecycleAwarePresenter {

        fun getMovies(query: String)

        fun getNextPage(query: String)

        fun openMovieDetails(activity: AppCompatActivity, currentFragment: Fragment, movieId: Int)

        fun goBack(activity: AppCompatActivity)

        fun saveFavorite(movie: MovieViewModel)

        fun removeFavorite(movie: MovieViewModel)
    }

    interface View : BaseView {

        fun showMovies(movies: List<MovieViewModel>)

        fun showNextPage(movies: List<MovieViewModel>)

        fun showErrorMessage(t: Throwable)
    }
}