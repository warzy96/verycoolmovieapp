package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.MovieCallback
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.data.MovieRepositoryImpl
import com.example.movieapp.data.MovieServiceImpl
import com.example.movieapp.domain.Movie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var movieRepository: MovieRepository = MovieRepositoryImpl(MovieServiceImpl())
    private lateinit var movieCallback : MovieCallback
    private val viewAdapter by lazy { MovieListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)

        movieRecyclerView.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        movieRecyclerView.addItemDecoration(
            DividerItemDecoration(movieRecyclerView.context, viewManager.orientation)
        )

        swipeMovieContainer.setOnRefreshListener {
            loadMovies()
        }

        getMoviesButton.setOnClickListener {
            loadMovies()
        }

        movieCallback = object : MovieCallback {
            override fun moviesUpdated(movies: List<Movie>) {
                viewAdapter.setData(movies)
                viewAdapter.notifyDataSetChanged()
                swipeMovieContainer.isRefreshing = false
            }
        }
    }

    class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
        private var movieList: List<Movie> = listOf()

        class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            var mTextView = view.findViewById<TextView>(R.id.movieTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                = MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            holder.mTextView.text = movieList[position].title
        }

        override fun getItemCount() = movieList.size

        fun setData(movieList : List<Movie>) {
            this.movieList = movieList
        }
    }

    fun loadMovies() {
        movieRepository.getMovies(movieCallback)
    }
}
