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

    var movieList: MutableList<Movie> = mutableListOf()
    private var movieRepository: MovieRepository = MovieRepositoryImpl(MovieServiceImpl())
    private lateinit var movieCallback : MovieCallback

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MovieListAdapter(movieList)

        movieRecyclerView.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        movieRecyclerView.addItemDecoration(
            DividerItemDecoration(movieRecyclerView.getContext(),
                (viewManager as LinearLayoutManager).orientation)
        )

        swipeMovieContainer.setOnRefreshListener {
            loadMovies()
        }

        getMoviesButton.setOnClickListener {
            loadMovies()
        }

        movieCallback = object : MovieCallback {
            override fun moviesUpdated(movies: List<Movie>) {
                Log.d("update", movies.size.toString())
                movieList.clear()
                movieList.addAll(movies)
                viewAdapter.notifyDataSetChanged()
                swipeMovieContainer.isRefreshing = false
            }
        }
    }

    class MovieListAdapter(private val myDataset: List<Movie>) :
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

        class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            var mTextView = view.findViewById<TextView>(R.id.movieTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)

            return MovieViewHolder(view)
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            holder.mTextView.text = myDataset[position].title
        }

        override fun getItemCount() = myDataset.size
    }

    fun loadMovies() {
        movieRepository.getMovies(movieCallback)
    }

}
