package com.example.movieapp.ui.listener

import com.example.movieapp.data.view.model.ViewMovie

interface MovieClickListener {

    fun onMovieClicked(movie: ViewMovie)
}