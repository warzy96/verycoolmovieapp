package com.example.movieapp.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.data.contract.MovieDetailsContract
import com.example.movieapp.data.presenter.MovieDetailsPresenter
import com.example.movieapp.data.view.model.MovieDetailsViewModel
import com.example.movieapp.data.view.model.MovieViewModel
import com.example.movieapp.ui.MovieApplication.Companion.dependencyInjector
import com.example.movieapp.ui.utils.MovieUtils
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsContract.View {

    private val presenter by lazy { MovieDetailsPresenter() }
    private val imageLoader by lazy { dependencyInjector.getImageLoader() }

    companion object {
        private const val MOVIE_ID_EXTRA = "movie_id"
        private const val TAG = "MovieDetailsActivity"
        private const val GENRE_SEPARATOR = ", "

        @JvmStatic
        fun createIntent(context: Context, movieId: Int) = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(MOVIE_ID_EXTRA, movieId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieId: Int = intent.getSerializableExtra(MOVIE_ID_EXTRA) as Int

        presenter.setView(this)
        presenter.getMovieDetails(movieId)
    }

    override fun showMovieDetails(movie: MovieDetailsViewModel) {
        imageLoader.loadImage(movie.backdropPath, moviePoster)

        movieDetailsTitle.text = movie.title

        if (!movie.tagline.isNullOrBlank()) {
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

        movieDetailsReleaseDate.text = MovieUtils.formatDate(movie.releaseDate)

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

        movieDetailsGenre.text = movie.genres.map { it.name }.joinToString(GENRE_SEPARATOR)
    }

    fun openHomepage(homepage: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(homepage)
        startActivity(openURL)
    }

    override fun showErrorMessage(t: Throwable) {
        movieDetails.visibility = View.GONE
        Log.e(TAG, t.localizedMessage ?: R.string.default_network_error.toString())
        AlertDialog.Builder(this)
            .setTitle(R.string.network_error_title)
            .setMessage(R.string.movies_error_message)
            .setNeutralButton(R.string.neutral_button_text, { _, _ -> finish() })
            .show()
    }
}
