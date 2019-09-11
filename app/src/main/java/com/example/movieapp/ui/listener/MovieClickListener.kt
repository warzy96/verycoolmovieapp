package com.example.movieapp.ui.listener

import com.example.movieapp.ui.view.model.MovieViewModel

interface MovieClickListener {

    fun onMovieClicked(movie: MovieViewModel)
}