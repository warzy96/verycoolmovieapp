package com.example.movieapp.ui.activities

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.ui.movies.presenter.MovieListPresenter
import com.example.movieapp.ui.router.MovieListRouter
import com.example.movieapp.ui.view.model.MovieViewModel
import com.example.movieapp.ui.movies.fragments.MoviesFragment
import com.example.movieapp.ui.listener.FavoriteClickListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity(), FavoriteClickListener {

    companion object {
        private const val TAG = "MainActivity"
        private const val SESSION_ID = "MainSession"
    }

    private val composite = CompositeDisposable()
    private val session = getKoin().createScope(SESSION_ID, named<MainActivity>())
    private val presenter: MovieListPresenter by session.inject()
    private val movieListRouter: MovieListRouter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment, MoviesFragment.newInstance(MovieApi.POPULARITY_SORT_NUM))
            .addToBackStack(null).commit()

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                    movieListRouter.openFavorites(this)
                    true
                }
                R.id.popular -> {
                    movieListRouter.openPopularMovies(this)
                    true
                }
                R.id.bestRating -> {
                    movieListRouter.openBestRatedMovies(this)
                    true
                }
            }

            false
        }
    }

    override fun onStart() {
        presenter.onStart()
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
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.repeatCount == 0) {
            movieListRouter.goBack(this)
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
}
