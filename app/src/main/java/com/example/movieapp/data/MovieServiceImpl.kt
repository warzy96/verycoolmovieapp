package com.example.movieapp.data

import retrofit2.Call
import retrofit2.Response

class MovieServiceImpl : MovieService {

    override fun getMovies(movieObserver: MovieObserver) {
        val call = MovieApiFactory.getAPI().getMovies(MovieApiFactory.API_KEY)

        call.enqueue(object : retrofit2.Callback<MovieResults> {
            override fun onResponse(call: Call<MovieResults>, response: Response<MovieResults>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        movieObserver.moviesUpdated(ApiMapper.map(response.body()!!.results ?: listOf()))
                    }
                }
            }

            override fun onFailure(call: Call<MovieResults>, t: Throwable) {
            }
        })
    }

}