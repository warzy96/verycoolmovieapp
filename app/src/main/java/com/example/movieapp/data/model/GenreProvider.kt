package com.example.movieapp.data.model

import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.repository.MovieRepositoryProvider

class GenreProvider {

    companion object {
        var genreList: List<Genre>? = null

        fun getGenres(genresCallback: GenresCallback) {
            if (genreList != null) {
                genresCallback.onGenresFetched(genreList ?: listOf())
                return
            }

            MovieRepositoryProvider.getRepository().getGenres(object : GenresCallback {
                override fun onGenresFetched(genres: List<Genre>) {
                    genreList = genres
                    genresCallback.onGenresFetched(genres)
                }

                override fun onError(t: Throwable) {
                }
            })
        }
    }
}