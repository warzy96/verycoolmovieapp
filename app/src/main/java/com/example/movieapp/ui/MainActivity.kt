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

    var movieList: MutableList<Movie> = mutableListOf()
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
            override fun onMoviesFetched(movies: List<Movie>) {
                movieList.clear()
                movieList.addAll(movies)
                viewAdapter.notifyDataSetChanged()
                swipeMovieContainer.isRefreshing = false
            }
        }
    }

    inner class MovieListAdapter(private val myDataset: List<Movie>) :
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

        inner class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
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

            holder.view.setOnClickListener {
                val intent = android.content.Intent(
                    this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", myDataset[position])
                startActivity(intent)
            }
        }

        override fun getItemCount() = myDataset.size
    }

    fun loadMovies() {
        MovieRepositoryProvider.getRepository().getMovies(movieCallback)
    }
}
