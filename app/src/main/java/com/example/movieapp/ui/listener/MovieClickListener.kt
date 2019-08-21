package com.example.movieapp.ui.listener

import com.example.movieapp.domain.Movie

interface MovieClickListener {

    fun onMovieClicked(movie: Movie)
}