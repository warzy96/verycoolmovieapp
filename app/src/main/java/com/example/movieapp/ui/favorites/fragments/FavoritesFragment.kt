package com.example.movieapp.ui.favorites.fragments

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movieapp.R
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.favorites.FavoritesListContract
import com.example.movieapp.ui.favorites.presenter.FavoritesListPresenter
import com.example.movieapp.ui.listener.FavoriteClickListener
import com.example.movieapp.ui.listener.MovieClickListener
import com.example.movieapp.ui.view.model.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.android.synthetic.main.movie_item.view.*
import org.koin.android.ext.android.getKoin
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
            LayoutInflater.from(activity),
            object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (model == clickedMovie?.posterPath) {
                        startPostponedEnterTransition()
                    }

                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    if (model == clickedMovie?.posterPath) {
                        startPostponedEnterTransition()
                    }

                    return false
                }
            })
    }
    private val session = getKoin().getOrCreateScope(SESSION_ID, named<FavoritesFragment>())
    private val presenter: FavoritesListPresenter by session.inject()
    private lateinit var fragmentView: View
    private var clickedMovie: MovieViewModel? = null

    fun setActivity(activity: AppCompatActivity) {
        presenter.setActivity(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()

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
        clickedMovie = movie
        val itemPosition = moviesAdapter.getMoviePosition(movie)
        val layoutManager = (fragmentView.movieRecyclerView.layoutManager as LinearLayoutManager)

        for (i in layoutManager.findFirstVisibleItemPosition()..layoutManager.findLastVisibleItemPosition()) {
            if (i == itemPosition && i != layoutManager.findLastVisibleItemPosition()) {
                continue
            }

            val animation = AnimationUtils
                .loadAnimation(fragmentView.movieRecyclerView.findViewHolderForAdapterPosition(i)?.itemView?.context, android.R.anim.slide_out_right)

            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationEnd(p0: Animation?) {
                    fragmentView.movieRecyclerView.findViewHolderForAdapterPosition(i)?.itemView?.alpha = 0f
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationStart(p0: Animation?) {

                }
            })

            if (i == layoutManager.findLastVisibleItemPosition()) {
                if (i == itemPosition) {
                    presenter.openMovieDetails(
                        movie.id,
                        fragmentView.movieRecyclerView.findViewHolderForAdapterPosition(itemPosition)?.itemView?.movieListPoster as View,
                        fragmentView.movieRecyclerView.findViewHolderForAdapterPosition(itemPosition)?.itemView?.movieListPoster?.transitionName
                            ?: resources.getString(R.string.poster)
                    )
                    continue
                } else {
                    animation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationEnd(p0: Animation?) {
                            fragmentView.movieRecyclerView.findViewHolderForAdapterPosition(i)?.itemView?.alpha = 0f

                            presenter.openMovieDetails(
                                movie.id,
                                fragmentView.movieRecyclerView.findViewHolderForAdapterPosition(itemPosition)?.itemView?.movieListPoster as View,
                                fragmentView.movieRecyclerView.findViewHolderForAdapterPosition(itemPosition)?.itemView?.movieListPoster?.transitionName
                                    ?: resources.getString(R.string.poster)
                            )
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                        override fun onAnimationStart(p0: Animation?) {

                        }
                    })
                }
            }

            fragmentView.movieRecyclerView.findViewHolderForAdapterPosition(i)?.itemView?.startAnimation(animation)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
        presenter.onStart()

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
        moviesAdapter.remove(movie)
    }

    private fun loadMovies() {
        presenter.getFavorites()
    }
}
