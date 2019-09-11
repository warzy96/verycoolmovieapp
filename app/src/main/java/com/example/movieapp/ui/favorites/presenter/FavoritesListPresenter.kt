package com.example.movieapp.ui.favorites.presenter

import com.example.movieapp.ui.favorites.FavoritesListContract
import com.example.movieapp.ui.presenter.BasePresenter
import com.example.movieapp.data.usecases.GetFavoritesUseCase
import com.example.movieapp.data.usecases.RemoveFavoriteUseCase
import com.example.movieapp.data.usecases.SaveFavoriteUseCase
import com.example.movieapp.ui.view.model.MovieViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException

class FavoritesListPresenter : BasePresenter<FavoritesListContract.View>(), FavoritesListContract.Presenter, KoinComponent {

    companion object {
        private const val INITIAL_PAGE = 1
        private const val HTTP_RESET_PAGE_CODE = 422
    }

    private var view: FavoritesListContract.View? = null
    private var page = INITIAL_PAGE
    private val saveFavoriteUseCase: SaveFavoriteUseCase by inject()
    private val removeFavoriteUseCase: RemoveFavoriteUseCase by inject()
    private val getFavoritesUseCase: GetFavoritesUseCase by inject()

    override fun setView(view: FavoritesListContract.View) {
        this.view = view
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

    override fun getFavorites() {
        composite.add(
            getFavoritesUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map {
                    for (movie in it) {
                        movie.favorite = true
                    }

                    it
                }
                .subscribe(this::onMoviesSuccess, this::onMovieError)
        )
    }

    fun onMoviesSuccess(movies: List<MovieViewModel>) {
        view?.showMovies(movies)
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