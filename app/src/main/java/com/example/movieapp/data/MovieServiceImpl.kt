package com.example.movieapp.data

import android.util.Log
import com.example.movieapp.domain.Movie
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class MovieServiceImpl : MovieService {

    override fun getMovies() : List<Movie> {
        val call = MovieAPIFactory.getAPI().getMovies(MovieAPIFactory.API_KEY)

        val movieResult = call.execute().body()

        return ApiMapper.map(movieResult!!.results!!)
    }

}