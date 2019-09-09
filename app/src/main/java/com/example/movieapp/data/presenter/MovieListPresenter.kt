package com.example.movieapp.data.presenter

import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.use_case.*
import com.example.movieapp.data.view.model.MovieViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException

class MovieListPresenter : BasePresenter<MovieListContract.View>(), MovieListContract.Presenter, KoinComponent {

    companion object {
        private const val INITIAL_PAGE = 1
        private const val RESET_PAGE = 0
        private const val HTTP_RESET_PAGE_CODE = 422
    }

    private var view: MovieListContract.View? = null
    private var page = INITIAL_PAGE
    private val getMoviesUseCase: GetMoviesUseCase by inject()
    private val getMoviesSearchUseCase: GetMoviesSearchUseCase by inject()
    private val saveFavoriteUseCase: SaveFavoriteUseCase by inject()
    private val removeFavoriteUseCase: RemoveFavoriteUseCase by inject()

    override fun setView(view: MovieListContract.View) {
        this.view = view
    }

    override fun getMovies(query: String) {
        val request =
            if (query.isBlank()) {
                getMoviesUseCase.execute(INITIAL_PAGE)
            } else getMoviesSearchUseCase.execute(SearchMoviesRequest(INITIAL_PAGE, query))

        composite.add(
            request
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onMoviesSuccess, this::onMovieError)
        )
    }

    override fun getNextPage(query: String) {
        page++

        val request =
            if (query.isBlank()) {
                getMoviesUseCase.execute(page)
            } else getMoviesSearchUseCase.execute(SearchMoviesRequest(page, query))

        composite.add(
            request
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onNextPageSuccess, this::onMovieError)
        )
    }

    override fun saveFavorite(movie: MovieViewModel) {
        saveFavoriteUseCase.execute(movie)
    }

    override fun removeFavorite(movie: MovieViewModel) {
        removeFavoriteUseCase.execute(movie)
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