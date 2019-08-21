package com.example.movieapp.data.callback

import com.example.movieapp.data.model.Genre

interface GenresCallback {

    fun onGenresFetched(genres: List<Genre>)

    fun onError()
}