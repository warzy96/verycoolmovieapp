package com.example.movieapp.ui.listener

import com.example.movieapp.data.view.model.MovieViewModel

interface MovieClickListener {

    fun onMovieClicked(movie: MovieViewModel)
}