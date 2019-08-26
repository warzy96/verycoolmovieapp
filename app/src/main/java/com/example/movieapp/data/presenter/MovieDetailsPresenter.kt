package com.example.movieapp.data.presenter

import com.example.movieapp.data.api.model.ApiGenre
import com.example.movieapp.data.service.GenreProvider
import com.example.movieapp.data.service.callback.GenresCallback
import com.example.movieapp.data.service.callback.MovieDetailsCallback
import com.example.movieapp.data.contract.MovieDetailsContract
import com.example.movieapp.data.view.model.MovieViewModel
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import com.example.movieapp.ui.MovieApplication.Companion.dependencyInjector

class MovieDetailsPresenter : MovieDetailsContract.Presenter {

    private var view: MovieDetailsContract.View? = null
    private val repository by lazy { dependencyInjector.getRepository() }
    private val viewMapper by lazy { dependencyInjector.getViewMapper() }

    override fun setView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun getMovieDetails(movieId: Int) {
        repository.getMovie(movieId, object : MovieDetailsCallback {
            override fun onMovieDetailsFetched(movieDetails: MovieDetails) {
                view?.showMovieDetails(viewMapper.mapMovieDetailsToMovieDetailsViewModel(movieDetails))
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