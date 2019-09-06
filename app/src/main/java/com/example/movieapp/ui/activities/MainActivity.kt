package com.example.movieapp.ui.activities

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.data.presenter.MovieListPresenter
import com.example.movieapp.data.view.model.MovieViewModel
import com.example.movieapp.ui.fragments.MoviesFragment
import com.example.movieapp.ui.listener.MovieClickListener
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity(), MovieClickListener {

    companion object {
        private const val TAG = "MainActivity"
        private const val SESSION_ID = "MainSession"
    }

    private val composite = CompositeDisposable()
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
}
