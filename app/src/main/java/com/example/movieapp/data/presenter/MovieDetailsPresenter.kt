package com.example.movieapp.data.presenter

import com.example.movieapp.data.api.model.ApiGenre
import com.example.movieapp.data.api.model.GenreProvider
import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieDetailsCallback
import com.example.movieapp.data.contract.MovieDetailsContract
import com.example.movieapp.data.view.model.ViewMovie
import com.example.movieapp.domain.Movie
import com.example.movieapp.ui.MovieApplication.Companion.dependencyInjector

class MovieDetailsPresenter : MovieDetailsContract.Presenter {

    private var view: MovieDetailsContract.View? = null

    override fun setView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun getMovieDetails(movieId: Int) {
        dependencyInjector.getRepository().getMovie(movieId, object : MovieDetailsCallback {
            override fun onMovieDetailsFetched(movie: Movie) {
                view?.showMovieDetails(dependencyInjector.getViewMapper().mapMovieToViewMovie(movie))
            }

            override fun onError(t: Throwable) {
                view?.showErrorMessage()
            }
        })
    }

    override fun getGenres(movie: ViewMovie) {
        GenreProvider.getGenres(object : GenresCallback {
            override fun onGenresFetched(genres: List<ApiGenre>) {
                val genreStrings = mutableListOf<String>()

                for (id in movie.genreIds) {
                    val genre = genres.filter { g -> g.id?.equals(id) ?: false }.single().name

                    if (genre != null) {
                        genreStrings.add(genre)
                    }
                }

                view?.showGenres(genreStrings)
            }

            override fun onError(t: Throwable) {
                view?.onGenresError()
            }
        })
    }

    override fun onDestroy() {
        this.view = null
    }
}