package com.example.movieapp.data.presenter

import com.example.movieapp.data.service.callback.MovieCallback
import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.domain.Movie
import com.example.movieapp.ui.MovieApplication.Companion.dependencyInjector

class MovieListPresenter : MovieListContract.Presenter {

    private var view: MovieListContract.View? = null
    private val repository by lazy { dependencyInjector.getRepository() }
    private val viewMapper by lazy { dependencyInjector.getViewMapper() }

    override fun setView(view: MovieListContract.View) {
        this.view = view
    }

    override fun getMovies() {
        repository.getMovies(object : MovieCallback {
            override fun onMoviesFetched(movies: List<Movie>) {
                view?.showMovies(viewMapper.mapMoviesToViewMovies(movies))
            }

            override fun onError(t: Throwable) {
                view?.showErrorMessage(t)
            }
        })
    }

    override fun onDestroy() {
        this.view = null
    }
}