package com.example.movieapp.data.service

import com.example.movieapp.data.api.model.ApiGenre
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.service.callback.GenresCallback
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