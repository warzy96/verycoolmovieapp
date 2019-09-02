package com.example.movieapp.data.presenter

import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.mapper.ViewModelMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.view.model.MovieViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException

class MovieListPresenter : MovieListContract.Presenter(), KoinComponent {

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

    override fun getMovies(query: String) {
        val request = if (query.isBlank()) repository.getMovies() else repository.getMoviesSearchResult(query)

        composite.add(
            request
                .map(viewModelMapper::mapMoviesToMovieViewModels)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onMoviesSuccess, this::onMovieError)
        )
    }

    override fun getNextPage(query: String) {
        page++

        val request = if (query.isBlank()) repository.getMovies(page) else repository.getMoviesSearchResult(page, query)

        composite.add(
            request
                .map(viewModelMapper::mapMoviesToMovieViewModels)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onNextPageSuccess, this::onMovieError)
        )
    }

    fun onMoviesSuccess(movies: List<MovieViewModel>) {
        view?.showMovies(movies)
    }

    fun onNextPageSuccess(movies: List<MovieViewModel>) {
        view?.showNextPage(movies)
    }

    fun onMovieError(t: Throwable) {
        page = RESET_PAGE

        if ((t is HttpException && t.code() == HTTP_RESET_PAGE_CODE).not()) {
            view?.showErrorMessage(t)
        }
    }

    override fun onStop() {
        super.onStop()
        this.view = null
    }
}