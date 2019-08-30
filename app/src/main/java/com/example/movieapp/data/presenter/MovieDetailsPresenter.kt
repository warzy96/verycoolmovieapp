package com.example.movieapp.data.presenter

import com.example.movieapp.data.contract.MovieDetailsContract
import com.example.movieapp.data.use_case.GetMovieDetailsUseCase
import com.example.movieapp.data.view.model.MovieDetailsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent

class MovieDetailsPresenter : MovieDetailsContract.Presenter(), KoinComponent {

    private var view: MovieDetailsContract.View? = null

    override fun setView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun getMovieDetails(movieId: Int) {
        composite.add(
            GetMovieDetailsUseCase()
                .execute(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onMovieDetailsSuccess, this::onMovieDetailsError)
        )
    }

    fun onMovieDetailsSuccess(movieDetails: MovieDetailsViewModel) {
        view?.showMovieDetails(movieDetails)
    }

    fun onMovieDetailsError(t: Throwable) {
        view?.showErrorMessage(t)
    }

    override fun onDestroy() {
        this.view = null
    }
}