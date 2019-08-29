package com.example.movieapp.data.presenter

import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.use_case.GetMoviesSearchUseCase
import com.example.movieapp.data.use_case.GetMoviesUseCase
import com.example.movieapp.data.use_case.requests.SearchMoviesRequest
import com.example.movieapp.data.view.model.MovieViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import retrofit2.HttpException

class MovieListPresenter : MovieListContract.Presenter, KoinComponent {

    companion object {
        private const val INITIAL_PAGE = 1
        private const val RESET_PAGE = 0
        private const val HTTP_RESET_PAGE_CODE = 422
    }

    private var view: MovieListContract.View? = null
    private var page = INITIAL_PAGE
    private val getMoviesUseCase = GetMoviesUseCase()
    private val getMoviesSearchUseCase = GetMoviesSearchUseCase()

    override fun setView(view: MovieListContract.View) {
        this.view = view
    }

    override fun getMovies() {
        getMoviesUseCase
            .execute(INITIAL_PAGE)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onMoviesSuccess, this::onMovieError)
    }

    private fun getNextMovies(query: String) {
        getMoviesSearchUseCase
            .execute(SearchMoviesRequest(page, query))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onNextPageSuccess, this::onMovieError)
    }

    private fun getNextMovies() {
        getMoviesUseCase
            .execute(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onNextPageSuccess, this::onMovieError)
    }

    override fun getMoviesSearchResult(query: String) {
        if (query.isBlank()) {
            getMovies()
            return
        }

        getMoviesSearchUseCase
            .execute(SearchMoviesRequest(INITIAL_PAGE, query))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onMoviesSuccess, this::onMovieError)
    }

    fun onMoviesSuccess(movies: List<MovieViewModel>) {
        view?.showMovies(movies)
    }

    fun onNextPageSuccess(movies: List<MovieViewModel>) {
        view?.showNextPage(movies)
    }

    fun onMovieError(t: Throwable) {
        page = RESET_PAGE

        if (!(t is HttpException && t.code() == HTTP_RESET_PAGE_CODE)) {
            view?.showErrorMessage(t)
        }
    }

    override fun getNextPage(query: String) {
        page++

        if (query.isBlank()) {
            getNextMovies()
        } else {
            getNextMovies(query)
        }
    }

    override fun onDestroy() {
        this.view = null
    }
}