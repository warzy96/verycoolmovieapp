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
        private const val HTTP_RESET_PAGE_CODE = 422
    }

    private var view: MovieListContract.View? = null
    private var page = INITIAL_PAGE
    private val getMoviesUseCase: GetMoviesUseCase by inject()
    private val getMoviesSearchUseCase: GetMoviesSearchUseCase by inject()
    private val saveFavoriteUseCase: SaveFavoriteUseCase by inject()
    private val removeFavoriteUseCase: RemoveFavoriteUseCase by inject()
    private val getFavoritesUseCase: GetFavoritesUseCase by inject()

    override fun setView(view: MovieListContract.View) {
        this.view = view
    }

    override fun getMovies(query: String, sort: String) {
        val request =
            if (query.isBlank()) {
                page = INITIAL_PAGE
                getMoviesUseCase.execute(INITIAL_PAGE, sort)
            } else {
                getMoviesSearchUseCase.execute(SearchMoviesRequest(INITIAL_PAGE, query))
            }

        composite.add(
            getFavoritesUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess { favorites ->
                    request
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            this.onMoviesSuccess(it.map {
                                if (favorites.contains(it))
                                    it.favorite = true
                                it
                            })
                        }, this::onMovieError)
                }.subscribe()
        )
    }

    override fun getNextPage(query: String, sort: String) {
        page++

        val request =
            if (query.isBlank()) {
                getMoviesUseCase.execute(page, sort)
            } else getMoviesSearchUseCase.execute(SearchMoviesRequest(page, query))

        composite.add(
            getFavoritesUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSuccess { favorites ->
                    request
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            this.onNextPageSuccess(it.map {
                                if (favorites.contains(it))
                                    it.favorite = true
                                it
                            })
                        }, this::onMovieError)
                }.subscribe()

        )
    }

    override fun saveFavorite(movie: MovieViewModel) {
        composite.add(
            saveFavoriteUseCase.execute(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({}, {})
        )
    }

    override fun removeFavorite(movie: MovieViewModel) {
        composite.add(
            removeFavoriteUseCase.execute(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({}, {})
        )
    }

    fun onMoviesSuccess(movies: List<MovieViewModel>) {
        view?.showMovies(movies)
    }

    fun onNextPageSuccess(movies: List<MovieViewModel>) {
        view?.showNextPage(movies)
    }

    fun onMovieError(t: Throwable) {
        page = INITIAL_PAGE

        if ((t is HttpException && t.code() == HTTP_RESET_PAGE_CODE).not()) {
            view?.showErrorMessage(t)
        }
    }

    override fun onStop() {
        super.onStop()
        this.view = null
    }
}