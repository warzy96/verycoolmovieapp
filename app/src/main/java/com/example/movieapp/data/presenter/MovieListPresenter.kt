package com.example.movieapp.data.presenter

import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.view.model.MovieViewModel
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException

class MovieListPresenter : MovieListContract.Presenter, KoinComponent {

    companion object {
        private const val INITIAL_PAGE = 1
        private const val RESET_PAGE = 0
        private const val HTTP_RESET_PAGE_CODE = 422
    }

    private var view: MovieListContract.View? = null
    private val repository: MovieRepository by inject()
    private val viewModelMapper: ViewModelMapper by inject()
    private var page = INITIAL_PAGE

    override fun setView(view: MovieListContract.View) {
        this.view = view
    }

    override fun getMovies() {
        repository.getMovies()
            .subscribeOn(Schedulers.io())
            .map(viewModelMapper::mapMoviesToMovieViewModels)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onMoviesSuccess, this::onMovieDetailsError)
    }

    fun onMoviesSuccess(movies: List<MovieViewModel>) {
        view?.showMovies(movies)
    }

    fun onMovieDetailsError(t: Throwable) {
        page = RESET_PAGE

        if (!(t is HttpException && t.code() == HTTP_RESET_PAGE_CODE)) {
            view?.showErrorMessage(t)
        }
    }

    override fun getNextPage() {
        page++

        repository.getMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Movie>>() {
                override fun onSuccess(t: List<Movie>) {
                    view?.showNextPage(viewModelMapper.mapMoviesToMovieViewModels(t))
                }

                override fun onError(e: Throwable) {
                    page = RESET_PAGE

                    if (!(e is HttpException && e.code() == HTTP_RESET_PAGE_CODE)) {
                        view?.showErrorMessage(e)
                    }
                }
            })
    }

    override fun onDestroy() {
        this.view = null
    }
}