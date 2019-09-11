package com.example.movieapp.ui.view.model

import java.io.Serializable

class MovieViewModel(
    val id: Int,
    val title: String,
    val popularity: Double,
    val voteCount: Int,
    val voteAverage: Double,
    val posterPath: String,
    val releaseDate: String,
    var favorite: Boolean
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return this.id == (other as MovieViewModel).id
    }
}