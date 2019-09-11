package com.example.movieapp.ui.movies.fragments

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.ui.movies.MovieListContract
import com.example.movieapp.ui.movies.presenter.MovieListPresenter
import com.example.movieapp.ui.router.MovieListRouter
import com.example.movieapp.ui.view.model.MovieViewModel
import com.example.movieapp.ui.activities.MainActivity
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.listener.FavoriteClickListener
import com.example.movieapp.ui.listener.MovieClickListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.fragment_movies.view.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

class MoviesFragment(sortNum: Int) : Fragment(), MovieListContract.View, MovieClickListener {

    companion object {
        const val TAG = "MoviesFragment"
        private const val LOADING_OFFSET = 5
        private const val DEBOUNCE_TIME_MILLISECONDS = 500L
        private const val SESSION_ID = "MoviesSession"

        @JvmStatic
        fun newInstance(sortNum: Int) = MoviesFragment(sortNum)
    }

    private val composite = CompositeDisposable()
    private val moviesAdapter by lazy {
        MoviesAdapter(
            this,
            (activity as FavoriteClickListener),
            LayoutInflater.from(activity)
        )
    }
    private val session = getKoin().getOrCreateScope(SESSION_ID, named<MainActivity>())
    private val presenter: MovieListPresenter by session.inject()
    private var loading = false
    private lateinit var fragmentView: View
    private var sort: String = MovieApi.getSort(sortNum)
    private lateinit var searchDisposable: Disposable
    private lateinit var searchObservable: Flowable<String>
    private val movieListRouter: MovieListRouter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_movies, container, false)
        initMoviesRecyclerView(fragmentView)

        fragmentView.swipeMovieContainer.setOnRefreshListener {
            loadMovies()
        }

        searchObservable = Flowable.create<String>(
            { emitter ->
                fragmentView.searchBar.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        emitter.onNext(s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }
                })
            },
            BackpressureStrategy.BUFFER
        ).switchMap { t -> Flowable.just(t) }

        if (moviesAdapter.itemCount == 0) {
            loadMovies()
        }

        return fragmentView
    }

    override fun onMovieClicked(movie: MovieViewModel) {
        movieListRouter.openMovieDetails(activity as AppCompatActivity, movie.id)
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
    }

    override fun onResume() {
        searchDisposable = searchObservable
            .debounce(DEBOUNCE_TIME_MILLISECONDS, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ presenter.getMovies(it, sort) })
        composite.add(searchDisposable)
        super.onResume()
    }

    override fun onStop() {
        composite.remove(searchDisposable)
        searchDisposable.dispose()
        super.onStop()
    }

    fun initMoviesRecyclerView(fragmentView: View) {
        fragmentView.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity as Activity)
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
                        fragmentView.loadingText.visibility = View.VISIBLE
                        presenter.getNextPage(fragmentView.searchBar.text.toString(), sort)
                    }
                }
            })
        }
    }

    override fun showMovies(movies: List<MovieViewModel>) {
        moviesAdapter.setData(movies)
        fragmentView.swipeMovieContainer.isRefreshing = false

        searchBar.visibility = View.VISIBLE
        searchIcon.visibility = View.VISIBLE
        moviesErrorMessage.visibility = View.GONE
    }

    override fun showNextPage(movies: List<MovieViewModel>) {
        moviesAdapter.addData(movies)
        loadingText.visibility = View.GONE
        loading = false
    }

    override fun showErrorMessage(t: Throwable) {
        fragmentView.swipeMovieContainer.isRefreshing = false

        Log.e(TAG, t.localizedMessage ?: R.string.default_network_error.toString())
        AlertDialog.Builder(activity as Activity)
            .setTitle(R.string.network_error_title)
            .setMessage(R.string.movies_error_message)
            .setNeutralButton(R.string.neutral_button_text, { _, _ -> Unit })
            .show()

        searchBar.visibility = View.GONE
        searchIcon.visibility = View.GONE
        moviesErrorMessage.visibility = View.VISIBLE
    }

    private fun loadMovies() {
        presenter.getMovies(fragmentView.searchBar.text.toString(), sort)
    }
}
