package com.example.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.repository.MovieRepositoryProvider
import com.example.movieapp.domain.Movie
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.listener.MovieClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieCallback: MovieCallback
    private val viewAdapter by lazy {
        MoviesAdapter(
            object : MovieClickListener {
                override fun onMovieClicked(movie: Movie) {
                    startActivity(MovieDetailsActivity.createIntent(this@MainActivity, movie))
                }
            },
            resources.getDimension(R.dimen.poster_list_width).toInt(),
            resources.getDimension(R.dimen.poster_list_height).toInt(),
            LayoutInflater.from(this),
            Glide.with(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)

        movieRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        movieRecyclerView.addItemDecoration(
            DividerItemDecoration(movieRecyclerView.context, viewManager.orientation)
        )

        swipeMovieContainer.setOnRefreshListener {
            loadMovies()
        }

        movieCallback = object : MovieCallback {
            override fun onMoviesFetched(movies: List<Movie>) {
                viewAdapter.setData(movies)
                swipeMovieContainer.isRefreshing = false
            }

            override fun onError() {
            }
        }

        loadMovies()
    }

    private fun loadMovies() {
        MovieRepositoryProvider.getRepository().getMovies(movieCallback)
    }
}
