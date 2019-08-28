package com.example.movieapp.data.presenter

import com.example.movieapp.data.contract.MovieDetailsContract
import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.domain.MovieDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieDetailsPresenter : MovieDetailsContract.Presenter, KoinComponent {

    private var view: MovieDetailsContract.View? = null
    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()

    override fun setView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun getMovieDetails(movieId: Int) {
        repository.getMovie(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onMovieDetailsSuccess, this::onMovieDetailsError)
    }

    fun onMovieDetailsSuccess(movieDetails: MovieDetails) {
        view?.showMovieDetails(viewModelMapper.mapMovieDetailsToMovieDetailsViewModel(movieDetails))
    }

    fun onMovieDetailsError(t: Throwable) {
        view?.showErrorMessage(t)
    }

    override fun onDestroy() {
        this.view = null
    }
}