package com.example.movieapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.presenter.MovieListPresenter
import com.example.movieapp.data.view.model.MovieViewModel
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.listener.MovieClickListener
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity(), MovieListContract.View, MovieClickListener {

    companion object {
        private const val TAG = "MainActivity"
        private const val SESSION_ID = "MainSession"
    }

    private val moviesAdapter by lazy { MoviesAdapter(this, LayoutInflater.from(this)) }
    private val session = getKoin().createScope(SESSION_ID, named<MainActivity>())
    private val presenter: MovieListPresenter by session.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.setView(this)

        initMoviesRecyclerView()

        swipeMovieContainer.setOnRefreshListener {
            loadMovies()
        }

        loadMovies()
    }

    fun initMoviesRecyclerView() {
        movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = moviesAdapter

            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
        }
    }

    override fun onDestroy() {
        session.close()
        super.onDestroy()
    }

    override fun onMovieClicked(movie: MovieViewModel) {
        startActivity(MovieDetailsActivity.createIntent(this@MainActivity, movie.id))
    }

    override fun showMovies(movies: List<MovieViewModel>) {
        moviesAdapter.setData(movies)
        swipeMovieContainer.isRefreshing = false
    }

    override fun showErrorMessage(t: Throwable) {
        swipeMovieContainer.isRefreshing = false

        Log.e(TAG, t.localizedMessage ?: R.string.default_network_error.toString())
        AlertDialog.Builder(this@MainActivity)
            .setTitle(R.string.network_error_title)
            .setMessage(R.string.movies_error_message)
            .setNeutralButton(R.string.neutral_button_text, { _, _ -> Unit })
            .show()
    }

    private fun loadMovies() {
        presenter.getMovies()
    }
}
