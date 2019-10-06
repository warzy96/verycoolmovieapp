package com.example.movieapp.ui.movies.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movieapp.R
import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.data.util.ImageLoader
import com.example.movieapp.ui.activities.MainActivity
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.listener.FavoriteClickListener
import com.example.movieapp.ui.listener.MovieClickListener
import com.example.movieapp.ui.movies.MovieListContract
import com.example.movieapp.ui.movies.presenter.MovieListPresenter
import com.example.movieapp.ui.view.model.MovieViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.android.synthetic.main.movie_item.view.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

class MoviesFragment(sortNum: Int) : Fragment(), MovieListContract.View, MovieClickListener, FavoriteClickListener {

    companion object {
        const val TAG = "MoviesFragment"
        private const val LOADING_OFFSET = 5
        private const val DEBOUNCE_TIME_MILLISECONDS = 500L

        @JvmStatic
        fun newInstance(sortNum: Int) = MoviesFragment(sortNum)
    }

    private var composite = CompositeDisposable()
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
    private val session = getKoin().getOrCreateScope(MainActivity.SESSION_ID, named<MainActivity>())
    private val presenter: MovieListPresenter by session.inject()
    private var loading = false
    private lateinit var fragmentView: View
    private var sort: String = MovieApi.getSort(sortNum)
    private lateinit var searchDisposable: Disposable
    private lateinit var searchObservable: Flowable<String>
    private val imageLoader: ImageLoader by inject()
    private var clickedMovie: MovieViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()

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

        return fragmentView
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
        presenter.onStart()
        presenter.setView(this)
        if (moviesAdapter.itemCount == 0) {
            loadMovies()
        }
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
        presenter.onStop()
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
                        fragmentView.loadingText.translationY = 0f
                        fragmentView.loadingText.visibility = View.VISIBLE
                        presenter.getNextPage(fragmentView.searchBar.text.toString(), sort)
                    }
                }
            })
        }

    }

    private fun animateHidingBottomNavigation(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "translationY", 0f, view.height.toFloat())
        animator.duration = 500
        animator.interpolator = BounceInterpolator()
        animator.start()
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
        animateHidingBottomNavigation(loadingText)
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

    override fun onToggleOn(movie: MovieViewModel) {
        presenter.saveFavorite(movie)

        val movieView = fragmentView.movieRecyclerView.findViewHolderForLayoutPosition(moviesAdapter.getMoviePosition(movie))
        val moviePoster = (movieView as MoviesAdapter.MovieViewHolder).view.movieListPoster

        val viewToAnimate = viewToAnimate(movie, moviePoster)

        val moviePosition = getPositionOf(moviePoster)
        val favoritesPosition = getPositionOf((activity as MainActivity).findViewById(R.id.favorites))

        (activity as MainActivity).mainLayout.addView(viewToAnimate)

        val xAnimator = objectAnimatorOfFloat(viewToAnimate, "x", moviePosition[0].toFloat(), favoritesPosition[0].toFloat() + moviePoster.width / 2)
        val yAnimator = objectAnimatorOfFloat(
            viewToAnimate,
            "y",
            moviePosition[1].toFloat() - moviePoster.height / 2,
            favoritesPosition[1].toFloat() - moviePoster.height / 2
        )

        AnimatorSet().apply {
            play(xAnimator).with(yAnimator)

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    if (activity != null) {
                        (activity as MainActivity).mainLayout.removeView(viewToAnimate)
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                    if (activity != null) {
                        (activity as MainActivity).mainLayout.removeView(viewToAnimate)
                    }
                }
            })
            start()
        }
    }

    override fun onToggleOff(movie: MovieViewModel) {
        presenter.removeFavorite(movie)

        val movieView = fragmentView.movieRecyclerView.findViewHolderForLayoutPosition(moviesAdapter.getMoviePosition(movie))
        val moviePoster = (movieView as MoviesAdapter.MovieViewHolder).view.movieListPoster

        val viewToAnimate = viewToAnimate(movie, moviePoster)

        val moviePosition = getPositionOf(moviePoster)
        val favoritesPosition = getPositionOf((activity as MainActivity).findViewById(R.id.favorites))

        (activity as MainActivity).mainLayout.addView(viewToAnimate)

        viewToAnimate.x = favoritesPosition[0].toFloat()
        val yAnimation1 = ObjectAnimator.ofFloat(
            viewToAnimate,
            "y",
            favoritesPosition[1].toFloat() - moviePoster.height / 2,
            favoritesPosition[1].toFloat() - 3 * moviePoster.height / 2
        )
        yAnimation1.interpolator = DecelerateInterpolator()
        val yAnimation2 = ObjectAnimator.ofFloat(
            viewToAnimate,
            "y",
            favoritesPosition[1].toFloat() - 3 * moviePoster.height / 2,
            favoritesPosition[1].toFloat() - moviePoster.height / 2
        )
        yAnimation2.interpolator = AccelerateInterpolator()

        yAnimation1.apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    yAnimation2.apply {
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                (activity as MainActivity).mainLayout.removeView(viewToAnimate)
                            }

                            override fun onAnimationCancel(animation: Animator?) {
                                (activity as MainActivity).mainLayout.removeView(viewToAnimate)
                            }
                        })
                        start()
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                    (activity as MainActivity).mainLayout.removeView(viewToAnimate)
                }
            })

            start()
        }
    }

    private fun loadMovies() {
        presenter.getMovies(fragmentView.searchBar.text.toString(), sort)
    }

    private fun getPositionOf(view: View?): IntArray {
        val position = intArrayOf(0, 0)
        view?.getLocationOnScreen(position)
        return position
    }

    private fun viewToAnimate(movie: MovieViewModel, imageView: ImageView): ImageView {
        val viewToAnimate = ImageView(activity)
        imageLoader.loadImage(movie.posterPath, viewToAnimate)
        val layoutParams = imageView.layoutParams
        layoutParams.width = imageView.width
        layoutParams.height = imageView.height
        viewToAnimate.layoutParams = layoutParams
        return viewToAnimate
    }

    private fun objectAnimatorOfFloat(view: View, propertyName: String, startValue: Float, endValue: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(view, propertyName, startValue, endValue)
        animator.duration = 1000
        animator.interpolator = BounceInterpolator()
        return animator
    }
}
