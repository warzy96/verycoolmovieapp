package com.example.movieapp.data.api.model

import com.example.movieapp.data.DependencyInjector
import com.example.movieapp.data.callback.GenresCallback

class GenreProvider {

    companion object {
        var genreList: List<ApiGenre>? = null

        fun getGenres(genresCallback: GenresCallback) {
            if (genreList != null) {
                genresCallback.onGenresFetched(genreList ?: listOf())
                return
            }

            DependencyInjector.getRepository().getGenres(object : GenresCallback {
                override fun onGenresFetched(genres: List<ApiGenre>) {
                    genreList = genres
                    genresCallback.onGenresFetched(genres)
                }

                override fun onError() {
                }
            })
        }
    }
}