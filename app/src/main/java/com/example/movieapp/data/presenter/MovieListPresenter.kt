package com.example.movieapp.data.presenter

import com.example.movieapp.data.DependencyInjector
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.domain.Movie

class MovieListPresenter : MovieListContract.Presenter {

    private var view: MovieListContract.View? = null

    override fun setView(view: MovieListContract.View) {
        this.view = view
    }

    override fun getMovies() {
        DependencyInjector.getRepository().getMovies(object : MovieCallback {
            override fun onMoviesFetched(movies: List<Movie>) {
                view?.showMovies(DependencyInjector.getViewMapper().mapMoviesToViewMovies(movies))
            }

            override fun onError() {
                view?.showErrorMessage()
            }
        })
    }

    override fun onDestroy() {
        this.view = null
    }
}