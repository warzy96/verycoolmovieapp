package com.example.movieapp.data.callback

import com.example.movieapp.data.api.model.ApiGenre

interface GenresCallback {

    fun onGenresFetched(genres: List<ApiGenre>)

    fun onError(t: Throwable)
}