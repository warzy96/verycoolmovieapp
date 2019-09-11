package com.example.movieapp.ui.moviedetails.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.movieapp.R
import com.example.movieapp.ui.moviedetails.MovieDetailsContract
import com.example.movieapp.ui.moviedetails.presenter.MovieDetailsPresenter
import com.example.movieapp.data.util.ImageLoader
import com.example.movieapp.ui.activities.MainActivity
import com.example.movieapp.ui.moviedetails.view.MovieDetailsViewModel
import com.example.movieapp.ui.presenter.router.MovieListRouter
import com.example.movieapp.ui.utils.MovieUtils
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import kotlin.properties.Delegates

class MovieDetailsFragment : Fragment(), MovieDetailsContract.View {

    companion object {
        private const val MOVIE_ID_PARAM = "movie_id"
        private val TAG = "MovieDetailsFragment"
        private const val GENRE_SEPARATOR = ", "
        private const val SESSION_ID = "MovieDetailsSession"

        @JvmStatic
        fun newInstance(movieId: Int) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID_PARAM, movieId)
                }
            }
    }

    private val session = getKoin().getOrCreateScope(SESSION_ID, named<MovieDetailsFragment>())
    private val presenter: MovieDetailsPresenter by session.inject()
    private val imageLoader: ImageLoader by inject()
    private var movieId: Int by Delegates.notNull()
    private lateinit var fragmentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(MOVIE_ID_PARAM)
            presenter.setView(this)
            presenter.getMovieDetails(movieId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_movie_details, container, false)
        return fragmentView
    }

    override fun showMovieDetails(movie: MovieDetailsViewModel) {
        fragmentView.apply {
            imageLoader.loadImage(movie.backdropPath, moviePoster)
            imageLoader.loadImage(movie.posterPath, movieDetailsPoster)

            movieDetailsTitle.text = movie.title

            if (!movie.tagline.isNullOrBlank()) {
                movieDetailsTagline.visibility = View.VISIBLE
                movieDetailsTagline.text = MovieUtils.formatTagline(movie.tagline)
            } else {
                movieDetailsTagline.visibility = View.GONE
            }

            if (movie.title != movie.originalTitle) {
                movieDetailsTitle.text = MovieUtils.formatFullTitle(movie.title, movie.originalTitle)
            }

            movieDetailsVote.text = MovieUtils.formatVotes(movie.voteAverage, movie.voteCount)
            movieDetailsRating.rating = movie.voteAverage.toFloat()
            movieDetailsOverview.text = movie.overview

            if (!movie.releaseDate.isBlank()) {
                movieDetailsReleaseDate.text = MovieUtils.formatDate(movie.releaseDate)
            } else {
                movieDetailsReleaseDate.visibility = View.GONE
            }

            if (movie.runtime != null) {
                movieDetailsRuntime.text = MovieUtils.formatRuntime(movie.runtime)
            } else {
                movieDetailsRuntime.visibility = View.GONE
            }

            if (movie.homepage != null) {
                readMoreButton.setOnClickListener {
                    openHomepage(movie.homepage)
                }
            } else {
                readMoreButton.visibility = View.GONE
            }

            if (movie.isAdult) {
                movieDetailsAdult.visibility = View.VISIBLE
            }

            if (!movie.genres.isEmpty()) {
                movieDetailsGenre.text = movie.genres.map { it.name }.joinToString(GENRE_SEPARATOR)
            } else {
                movieDetailsGenre.visibility = View.GONE
            }

            if (!movie.countries.isEmpty()) {
                movieDetailsCountries.text = movie.countries.map { it.name }.joinToString(GENRE_SEPARATOR)
            } else {
                movieDetailsCountries.visibility = View.GONE
            }
        }
    }

    fun openHomepage(homepage: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(homepage)
        startActivity(openURL)
    }

    override fun showErrorMessage(t: Throwable) {
        movieDetails.visibility = View.GONE
        Log.e(TAG, t.localizedMessage ?: R.string.default_network_error.toString())
        AlertDialog.Builder(activity as Activity)
            .setTitle(R.string.network_error_title)
            .setMessage(R.string.movies_error_message)
            .setNeutralButton(R.string.neutral_button_text, { _, _ -> Unit })
            .show()
    }
}
