package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.repository.MovieRepositoryProvider
import com.example.movieapp.domain.Movie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
            override fun onMoviesFetched(movies: List<Movie>) {
                viewAdapter.setData(movies)
                viewAdapter.notifyDataSetChanged()
                swipeMovieContainer.isRefreshing = false
            }
        }
    }

    inner class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
        private var movieList: List<Movie> = listOf()

        inner class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            var mTextView = view.findViewById<TextView>(R.id.movieTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                = MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            holder.view.setOnClickListener {
                val intent = android.content.Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", movieList[position])
                startActivity(intent)
            }
            holder.mTextView.text = movieList[position].title
        }

        override fun getItemCount() = movieList.size

        fun setData(movieList : List<Movie>) {
            this.movieList = movieList
        }
    }

    fun loadMovies() {
        MovieRepositoryProvider.getRepository().getMovies(movieCallback)
    }
}
