package com.example.movieapp.data.presenter

import android.util.Log
import com.example.movieapp.data.DependencyInjector
import com.example.movieapp.data.api.model.Genre
import com.example.movieapp.data.api.model.GenreProvider
import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.MovieDetailsCallback
import com.example.movieapp.data.contract.MovieDetailsContract
import com.example.movieapp.data.view.model.ViewMovie
import com.example.movieapp.domain.Movie

class MovieDetailsPresenter : MovieDetailsContract.Presenter {

    private var view: MovieDetailsContract.View? = null

    override fun setView(view: MovieDetailsContract.View) {
        this.view = view
    }

    override fun getMovieDetails(movieId: Int) {
        DependencyInjector.getRepository().getMovie(movieId, object : MovieDetailsCallback {
            override fun onMovieDetailsFetched(movie: Movie) {
                Log.e("presenter", movie.title)
                view?.showMovieDetails(DependencyInjector.getViewMapper().mapMovieToViewMovie(movie))
            }

            override fun onError() {
                view?.showErrorMessage()
            }
        })
    }

    override fun getGenres(movie: ViewMovie) {
        GenreProvider.getGenres(object : GenresCallback {
            override fun onGenresFetched(genres: List<Genre>) {
                val genreList = mutableListOf<String>()

                for (id in movie.genreIds) {
                    val genre = genres.filter { g -> g.id?.equals(id) ?: false }.single().name

                    if (genre != null) {
                        genreList.add(genre)
                    }
                }

                Log.e("pg", genreList.size.toString())
                view?.showGenres(genreList)
            }

            override fun onError() {
                view?.onGenresError()
            }
        })
    }

    override fun onDestroy() {
        this.view = null
    }
}