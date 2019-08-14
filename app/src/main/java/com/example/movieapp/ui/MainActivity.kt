package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.R
import com.example.movieapp.data.MovieCallback
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.data.MovieRepositoryImpl
import com.example.movieapp.data.MovieServiceImpl
import com.example.movieapp.domain.Movie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var movieList: MutableList<Movie> = mutableListOf()
    private var movieRepository: MovieRepository = MovieRepositoryImpl(MovieServiceImpl())

    private lateinit var movieCallback : MovieCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMoviesButton.setOnClickListener {
            loadMovies()
        }

        movieCallback = object : MovieCallback {
            override fun moviesUpdated(movies: List<Movie>) {
                movieList.clear()
                movieList.addAll(movies)

                movieListText.text = ("")
                for (movie in movieList) {
                    movieListText.setText(movieListText.text.toString() + movie.title + "\n")
                }
            }
        }
    }

    fun loadMovies() {
        movieRepository.getMovies(movieCallback)
    }

}
