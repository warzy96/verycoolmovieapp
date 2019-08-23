package com.example.movieapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.repository.MovieRepositoryProvider
import com.example.movieapp.domain.Movie
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.listener.MovieClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieClickListener {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var movieCallback: MovieCallback
    private val viewAdapter by lazy { MoviesAdapter(this, LayoutInflater.from(this)) }

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

            override fun onError(t: Throwable) {
                Log.e(TAG, t.localizedMessage ?: "")
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.network_error_title)
                    .setMessage(R.string.movies_error_message)
                    .setNeutralButton(R.string.neutral_button_text, { _, _ -> finish() })
                    .show()
            }
        }

        loadMovies()
    }

    override fun onMovieClicked(movie: Movie) {
        startActivity(MovieDetailsActivity.createIntent(this@MainActivity, movie))
    }

    private fun loadMovies() {
        MovieRepositoryProvider.getRepository().getMovies(movieCallback)
    }
}
