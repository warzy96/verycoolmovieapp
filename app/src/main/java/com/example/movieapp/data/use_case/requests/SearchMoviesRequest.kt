package com.example.movieapp.data.use_case.requests

data class SearchMoviesRequest(
    val page: Int,
    val query: String
)