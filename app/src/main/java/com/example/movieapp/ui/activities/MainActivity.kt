package com.example.movieapp.ui.activities

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.ui.movies.fragments.MoviesFragment
import com.example.movieapp.ui.movies.presenter.MovieListPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        const val SESSION_ID = "MainSession"
    }

    private val composite = CompositeDisposable()
    private val session = getKoin().createScope(SESSION_ID, named<MainActivity>())
    private val presenter: MovieListPresenter by session.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.setActivity(this)

        supportFragmentManager.beginTransaction().replace(R.id.fragment, MoviesFragment.newInstance(MovieApi.POPULARITY_SORT_NUM))
            .addToBackStack(null).commit()

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                    presenter.openFavorites()
                    true
                }
                R.id.popular -> {
                    presenter.openPopularMovies()
                    true
                }
                R.id.bestRating -> {
                    presenter.openBestRatedMovies()
                    true
                }
            }

            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.goBack()
        return true
    }

    override fun onStop() {
        composite.clear()
        super.onStop()
    }

    override fun onDestroy() {
        session.close()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.repeatCount == 0) {
            presenter.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}
