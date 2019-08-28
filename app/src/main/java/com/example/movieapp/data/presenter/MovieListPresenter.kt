package com.example.movieapp.data.presenter

import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
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
        repository.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onMoviesSuccess, this::onMovieDetailsError)
    }

    fun onMoviesSuccess(movies: List<Movie>) {
        view?.showMovies(viewModelMapper.mapMoviesToMovieViewModels(movies))
    }

    fun onMovieDetailsError(t: Throwable) {
        view?.showErrorMessage(t)
    }

    override fun onDestroy() {
        this.view = null
    }
}