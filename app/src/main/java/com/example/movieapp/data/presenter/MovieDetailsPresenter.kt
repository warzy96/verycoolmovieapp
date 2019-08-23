package com.example.movieapp.data.presenter

import com.example.movieapp.data.api.model.ApiGenre
import com.example.movieapp.data.api.model.GenreProvider
import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieDetailsCallback
import com.example.movieapp.data.contract.MovieDetailsContract
import com.example.movieapp.data.mapper.ViewMapper
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.view.model.ViewMovie
import com.example.movieapp.domain.Movie
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieDetailsPresenter : MovieDetailsContract.Presenter, KoinComponent {

    private var view: MovieDetailsContract.View? = null
    private val repository: MovieRepository by inject()
    private val viewMapper: ViewMapper by inject()
    private val genreProvider: GenreProvider by inject()

    override fun setView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun getMovieDetails(movieId: Int) {
        repository.getMovie(movieId, object : MovieDetailsCallback {
            override fun onMovieDetailsFetched(movie: Movie) {
                view?.showMovieDetails(viewMapper.mapMovieToViewMovie(movie))
            }

            override fun onError(t: Throwable) {
                view?.showErrorMessage(t)
            }
        })
    }

    override fun getGenres(movie: ViewMovie) {
        genreProvider.getGenres(object : GenresCallback {
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
                view?.onGenresError(t)
            }
        })
    }

    override fun onDestroy() {
        this.view = null
    }
}