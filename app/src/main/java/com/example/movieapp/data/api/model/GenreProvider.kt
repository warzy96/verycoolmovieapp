package com.example.movieapp.data.api.model

import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.repository.MovieRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class GenreProvider: KoinComponent {

    private val repository: MovieRepository by inject()
    var genreList: List<ApiGenre>? = null

    fun getGenres(genresCallback: GenresCallback) {
        if (genreList != null) {
            genresCallback.onGenresFetched(genreList ?: listOf())
            return
        }

        repository.getGenres(object : GenresCallback {
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