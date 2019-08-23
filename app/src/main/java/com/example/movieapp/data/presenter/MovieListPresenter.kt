package com.example.movieapp.data.presenter

import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.mapper.ViewMapper
import com.example.movieapp.data.mapper.ViewMapperImpl
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.domain.Movie
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieListPresenter : MovieListContract.Presenter, KoinComponent {

    private var view: MovieListContract.View? = null
    private val repository: MovieRepository by inject()
    private val viewMapper: ViewMapper by inject()

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