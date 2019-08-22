package com.example.movieapp.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.presenter.MovieListPresenter
import com.example.movieapp.data.view.model.ViewMovie
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.listener.MovieClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieListContract.View, MovieClickListener {

    private val viewAdapter by lazy { MoviesAdapter(this, LayoutInflater.from(this)) }
    private val presenter by lazy { MovieListPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)

        presenter.setView(this)

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

        loadMovies()
    }

    override fun onMovieClicked(movie: ViewMovie) {
        startActivity(MovieDetailsActivity.createIntent(this@MainActivity, movie.id))
    }

    override fun showMovies(movies: List<ViewMovie>) {
        moviesErrorMessage.visibility = View.GONE
        viewAdapter.setData(movies)
        swipeMovieContainer.isRefreshing = false
    }

    override fun showErrorMessage() {
        moviesErrorMessage.visibility = View.VISIBLE
        viewAdapter.setData(listOf())
        swipeMovieContainer.isRefreshing = false
    }

    private fun loadMovies() {
        presenter.getMovies()
    }
}
