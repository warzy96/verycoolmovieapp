package com.example.movieapp.data.service

import com.example.movieapp.data.api.MovieApiFactory
import com.example.movieapp.data.api.model.ApiMovieDetails
import com.example.movieapp.data.api.model.ApiMovieResults
import com.example.movieapp.data.mapper.ApiMapper
import com.example.movieapp.domain.Movie
import com.example.movieapp.domain.MovieDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieServiceImpl : MovieService, KoinComponent {

    private val apiMapper: ApiMapper by inject()

    override fun getMovies(moviesObserver: DisposableSingleObserver<List<Movie>>) {
        val call = MovieApiFactory.getApi().getMovies(MovieApiFactory.API_KEY)

        call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<ApiMovieResults>() {
                override fun onSuccess(t: ApiMovieResults) {
                    moviesObserver.onSuccess(apiMapper.mapApiMoviesToMovies(t.results ?: listOf()))
                }

                override fun onError(e: Throwable) {
                    moviesObserver.onError(e)
                }
            })
    }

    override fun getMovie(movieId: Int, movieObserver: DisposableSingleObserver<MovieDetails>) {
        val call = MovieApiFactory.getApi().getMovie(movieId, MovieApiFactory.API_KEY)

        call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<ApiMovieDetails>() {
                override fun onSuccess(t: ApiMovieDetails) {
                    movieObserver.onSuccess(apiMapper.mapApiMovieDetailsToMovieDetails(t))
                }

                override fun onError(e: Throwable) {
                    movieObserver.onError(e)
                }
            })
    }
}