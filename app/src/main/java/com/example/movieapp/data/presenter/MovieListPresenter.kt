package com.example.movieapp.data.presenter

import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.service.callback.MovieCallback
import com.example.movieapp.domain.Movie
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieListPresenter : MovieListContract.Presenter, KoinComponent {

    private var view: MovieListContract.View? = null
    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()

    override fun setView(view: MovieListContract.View) {
        this.view = view
    }

    override fun getMovies() {
        repository.getMovies(object : MovieCallback {
            override fun onMoviesFetched(movies: List<Movie>) {
                view?.showMovies(viewModelMapper.mapMoviesToMovieViewModels(movies))
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