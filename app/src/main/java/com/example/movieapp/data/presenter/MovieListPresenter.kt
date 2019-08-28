package com.example.movieapp.data.presenter

import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.domain.Movie
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

    override fun getMovies() = repository.getMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : DisposableSingleObserver<List<Movie>>() {
            override fun onSuccess(t: List<Movie>) {
                view?.showMovies(viewModelMapper.mapMoviesToMovieViewModels(t))
            }

            override fun onError(e: Throwable) {
                view?.showErrorMessage(e)
            }
        })

    override fun onDestroy() {
        this.view = null
    }
}