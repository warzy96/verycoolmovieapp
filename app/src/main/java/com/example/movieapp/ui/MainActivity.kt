package com.example.movieapp.ui

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.movieapp.R
import com.example.movieapp.data.MovieService
import com.example.movieapp.data.MovieServiceImpl
import com.example.movieapp.domain.Movie
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var getMoviesButton : Button
    lateinit var movieTextView : TextView
    var movieList : MutableList<Movie> = mutableListOf()
    private var movieService : MovieServiceImpl = MovieServiceImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieTextView = findViewById(R.id.movies)

        getMoviesButton = findViewById(R.id.get_movies_button)

        getMoviesButton.setOnClickListener {
            LoadMovies().execute(movieService)
        }
    }


    inner class LoadMovies : AsyncTask<MovieService, Void, List<Movie>>() {
        override fun doInBackground(vararg p0: MovieService?) : List<Movie> {
            return p0[0]!!.getMovies()
        }

        override fun onPostExecute(movies: List<Movie>?) {
            movieList = movies!!.toMutableList()

            movieTextView.setText("")
            for (movie in movieList) {
                movieTextView.setText(movieTextView.text.toString() + movie.title + "\n")
            }
        }

    }

}
