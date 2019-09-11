package com.example.movieapp.ui.favorites.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.ui.activities.MainActivity
import com.example.movieapp.ui.favorites.FavoritesListContract
import com.example.movieapp.ui.favorites.presenter.FavoritesListPresenter
import com.example.movieapp.ui.presenter.router.MovieListRouter
import com.example.movieapp.ui.view.model.MovieViewModel
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.listener.FavoriteClickListener
import com.example.movieapp.ui.listener.MovieClickListener
import kotlinx.android.synthetic.main.fragment_movies.view.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class FavoritesFragment : Fragment(), MovieClickListener, FavoritesListContract.View, FavoriteClickListener {

    companion object {
        const val TAG = "FavoritesFragment"
        private const val SESSION_ID = "FavoritesSession"

        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }

    private val moviesAdapter by lazy {
        MoviesAdapter(
            this,
           this,
            LayoutInflater.from(activity)
        )
    }
    private val session = getKoin().getOrCreateScope(SESSION_ID, named<FavoritesFragment>())
    private val presenter: FavoritesListPresenter by session.inject()
    private lateinit var fragmentView: View

    fun setActivity(activity: AppCompatActivity) {
        presenter.setActivity(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_favorites, container, false)
        initMoviesRecyclerView(fragmentView)

        fragmentView.swipeMovieContainer.setOnRefreshListener {
            loadMovies()
        }

        presenter.setView(this)

        return fragmentView
    }

    fun initMoviesRecyclerView(fragmentView: View) {
        fragmentView.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity as Activity)
            adapter = moviesAdapter

            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
        }
    }

    override fun onMovieClicked(movie: MovieViewModel) {
        presenter.openMovieDetails(movie.id)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
        presenter.setView(this)

        if (moviesAdapter.itemCount == 0) {
            loadMovies()
        }
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun showMovies(movies: List<MovieViewModel>) {
        moviesAdapter.setData(movies)
        fragmentView.swipeMovieContainer.isRefreshing = false
    }

    override fun showErrorMessage(t: Throwable) {
        fragmentView.swipeMovieContainer.isRefreshing = false

        Log.e(TAG, t.localizedMessage ?: R.string.default_network_error.toString())
        AlertDialog.Builder(activity as Activity)
            .setTitle(R.string.network_error_title)
            .setMessage(R.string.movies_error_message)
            .setNeutralButton(R.string.neutral_button_text, { _, _ -> Unit })
            .show()
    }

    override fun onToggleOn(movie: MovieViewModel) {
        presenter.saveFavorite(movie)
    }

    override fun onToggleOff(movie: MovieViewModel) {
        presenter.removeFavorite(movie)
    }

    private fun loadMovies() {
        presenter.getFavorites()
    }
}
