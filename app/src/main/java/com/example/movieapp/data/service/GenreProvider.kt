package com.example.movieapp.data.service

import com.example.movieapp.data.api.model.ApiGenre
import com.example.movieapp.data.service.callback.GenresCallback
import com.example.movieapp.ui.MovieApplication

class GenreProvider {

    companion object {
        var genreList: List<ApiGenre>? = null

        fun getGenres(genresCallback: GenresCallback) {
            if (genreList != null) {
                genresCallback.onGenresFetched(genreList ?: listOf())
                return
            }

            MovieApplication.dependencyInjector.getRepository().getGenres(object : GenresCallback {
                override fun onGenresFetched(genres: List<ApiGenre>) {
                    genreList = genres
                    genresCallback.onGenresFetched(genres)
                }

                override fun onError(t: Throwable) {
                    genresCallback.onError(t)
                }
            })
        }
    }
}