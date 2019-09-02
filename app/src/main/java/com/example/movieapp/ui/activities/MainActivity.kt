package com.example.movieapp.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.contract.MovieListContract
import com.example.movieapp.data.presenter.MovieListPresenter
import com.example.movieapp.data.view.model.MovieViewModel
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.listener.MovieClickListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MovieListContract.View, MovieClickListener {

    companion object {
        private const val TAG = "MainActivity"
        private const val SESSION_ID = "MainSession"
        private const val LOADING_OFFSET = 5
        private const val DEBOUNCE_TIME_MILLISECONDS = 500L
    }

    private val composite = CompositeDisposable()
    private val moviesAdapter by lazy { MoviesAdapter(this, LayoutInflater.from(this)) }
    private val session = getKoin().createScope(SESSION_ID, named<MainActivity>())
    private val presenter: MovieListPresenter by session.inject()
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.setView(this)

        initMoviesRecyclerView()

        swipeMovieContainer.setOnRefreshListener {
            loadMovies()
        }

        val searchObservable = Flowable.create<String>(
            { emitter ->
                searchBar.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        emitter.onNext(p0.toString())
                    }

                    override fun afterTextChanged(p0: Editable?) {
                    }
                })
            },
            BackpressureStrategy.BUFFER
        ).switchMap { t -> Flowable.just(t) }

        composite.add(
            searchObservable
                .debounce(DEBOUNCE_TIME_MILLISECONDS, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ presenter.getMovies(it) })
        )

        loadMovies()
    }

    fun initMoviesRecyclerView() {
        movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = moviesAdapter

            addItemDecoration(DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // When movie, which is on position itemCount - LOADING_OFFSET, becomes completely visible
                    // new page of movies will be fetched
                    if (!loading && (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        == adapter?.itemCount?.minus(LOADING_OFFSET)
                    ) {
                        loading = true
                        loadingText.visibility = View.VISIBLE
                        presenter.getNextPage(searchBar.text.toString())
                    }
                }
            })
        }
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

    override fun onMovieClicked(movie: MovieViewModel) {
        startActivity(MovieDetailsActivity.createIntent(this@MainActivity, movie.id))
    }

    override fun showMovies(movies: List<MovieViewModel>) {
        moviesAdapter.setData(movies)
        swipeMovieContainer.isRefreshing = false
    }

    override fun showNextPage(movies: List<MovieViewModel>) {
        moviesAdapter.addData(movies)
        loadingText.visibility = View.GONE
        loading = false
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
        presenter.getMovies(searchBar.text.toString())
    }
}
