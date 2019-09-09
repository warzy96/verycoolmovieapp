package com.example.movieapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.presenter.MovieListPresenter
import com.example.movieapp.data.view.model.MovieViewModel
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.fragments.MoviesFragment
import com.example.movieapp.ui.listener.FavoriteClickListener
import com.example.movieapp.ui.listener.MovieClickListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity(), MovieListContract.View, MovieClickListener, FavoriteClickListener {

    companion object {
        private const val TAG = "MainActivity"
        private const val SESSION_ID = "MainSession"
    }

    private val composite = CompositeDisposable()
    private val moviesAdapter by lazy { MoviesAdapter(this, this, LayoutInflater.from(this)) }
    private val session = getKoin().createScope(SESSION_ID, named<MainActivity>())
    private val presenter: MovieListPresenter by session.inject()
    private lateinit var movieListFragment: MoviesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieListFragment = MoviesFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragment, movieListFragment).addToBackStack(null).commit()
    }

    override fun onMovieClicked(movie: MovieViewModel) {
        presenter.openMovieDetails(this, movieListFragment, movie.id)
    }

    override fun onStart() {
        presenter.onStart()
        presenter.setView(movieListFragment)
        super.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        composite.clear()
        super.onStop()
    }

    override fun onDestroy() {
        session.close()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.getRepeatCount() == 0) {
            presenter.goBack(this)
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onToggleOn(movie: MovieViewModel) {
        presenter.saveFavorite(movie)
    }

    override fun onToggleOff(movie: MovieViewModel) {
        presenter.removeFavorite(movie)
    }


    override fun showMovies(movies: List<MovieViewModel>) {
        moviesAdapter.setData(movies)
        swipeMovieContainer.isRefreshing = false
    }

    override fun showNextPage(movies: List<MovieViewModel>) {
        moviesAdapter.addData(movies)
        loadingText.visibility = View.GONE
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
}
