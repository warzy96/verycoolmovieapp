package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.movieapp.R
import com.example.movieapp.data.MovieObserver
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.data.MovieRepositoryImpl
import com.example.movieapp.data.MovieServiceImpl
import com.example.movieapp.domain.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var getMoviesButton: Button
    lateinit var movieTextView: TextView

    var movieList: MutableList<Movie> = mutableListOf()
    private var movieRepository: MovieRepository = MovieRepositoryImpl(MovieServiceImpl())

    private lateinit var movieObserver : MovieObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieTextView = findViewById(R.id.movies)

        getMoviesButton = findViewById(R.id.get_movies_button)
        getMoviesButton.setOnClickListener {
            loadMovies()
        }

        movieObserver = object : MovieObserver {
            override fun moviesUpdated(movies: List<Movie>) {
                movieList.clear()
                movieList.addAll(movies)

                movieTextView.setText("")
                for (movie in movieList) {
                    movieTextView.setText(movieTextView.text.toString() + movie.title + "\n")
                }
            }
        }
    }

    fun loadMovies() {
        movieRepository.getMovies(movieObserver)
    }

}
