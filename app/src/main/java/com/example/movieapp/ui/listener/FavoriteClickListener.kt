package com.example.movieapp.ui.listener

import com.example.movieapp.data.view.model.MovieViewModel

interface FavoriteClickListener {

    fun onToggleOn(movie: MovieViewModel)

    fun onToggleOff(movie: MovieViewModel)
}