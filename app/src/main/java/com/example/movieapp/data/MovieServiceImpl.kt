package com.example.movieapp.data

import retrofit2.Call
import retrofit2.Response

class MovieServiceImpl : MovieService {

    override fun getMovies(movieCallback: MovieCallback) {
        val call = MovieApiFactory.getAPI().getMovies(MovieApiFactory.API_KEY)

        call.enqueue(object : retrofit2.Callback<MovieResults> {

            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                if (response.isSuccessful ) {
                    movieCallback.moviesUpdated(mapper.map(response.body()?.results ?: listOf()))
                }
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
            }

        })
    }

    companion object {
        val mapper = ApiMapperImpl()
    }
}