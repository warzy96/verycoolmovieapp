package com.example.movieapp.ui.moviedetails.presenter

import com.example.movieapp.ui.moviedetails.MovieDetailsContract
import com.example.movieapp.ui.presenter.BasePresenter
import com.example.movieapp.data.usecases.GetMovieDetailsUseCase
import com.example.movieapp.ui.moviedetails.view.MovieDetailsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieDetailsPresenter : BasePresenter<MovieDetailsContract.View>(), MovieDetailsContract.Presenter, KoinComponent {

    private var view: MovieDetailsContract.View? = null
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase by inject()

    override fun setView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun getMovieDetails(movieId: Int) {
        composite.add(
            getMovieDetailsUseCase
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
}