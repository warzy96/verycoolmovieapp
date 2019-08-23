package com.example.movieapp.ui.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.data.contract.MovieDetailsContract
import com.example.movieapp.data.presenter.MovieDetailsPresenter
import com.example.movieapp.data.view.model.ViewMovie
import com.example.movieapp.ui.MovieApplication.Companion.dependencyInjector
import com.example.movieapp.ui.utils.MovieUtils
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity(), MovieDetailsContract.View {

    private val presenter by lazy { MovieDetailsPresenter() }

    companion object {
        private const val MOVIE_ID_EXTRA = "movie_id"

        private const val RUNTIME_UNIT = "min"
        private const val QUOTE = "\""

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

    override fun showMovieDetails(movie: ViewMovie) {
        dependencyInjector.getImageLoader().loadImage(movie.posterPath, moviePoster)

        movieDetailsTitle.text = movie.title

        if (movie.tagline != null && movie.tagline != "") {
            movieDetailsTagline.text = QUOTE + movie.tagline + QUOTE
        } else {
            movieDetailsTagline.visibility = View.GONE
        }

        if (movie.title != (movie.originalTitle)) {
            movieDetailsTitle.text = movieDetailsTitle.text.toString() + " " + MovieUtils.formatOriginalTitle(movie.originalTitle)
        }

        movieDetailsVote.text = MovieUtils.formatVotes(movie.voteAverage, movie.voteCount)
        movieDetailsRating.rating = movie.voteAverage.toFloat() / 2
        movieDetailsOverview.text = movie.overview

        movieDetailsReleaseDate.text = MovieUtils.formatDate(movie.releaseDate)

        if (movie.runtime != null) {
            movieDetailsRuntime.text = movie.runtime.toString() + " " + RUNTIME_UNIT
        } else {
            movieDetailsRuntimeTxt.visibility = View.GONE
            movieDetailsRuntime.visibility = View.GONE
        }

        if (movie.homepage != null) {
            readMoreButton.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(movie.homepage)
                startActivity(openURL)
            }
        } else {
            readMoreButton.visibility = View.GONE
        }

        if (movie.isAdult) {
            movieDetailsAdult.visibility = View.VISIBLE
        }

        presenter.getGenres(movie)
    }

    override fun showGenres(genres: List<String>) {
        movieDetailsGenre.text = genres.joinToString(", ")
    }

    override fun onGenresError(t: Throwable) {
    }

    override fun showErrorMessage(t: Throwable) {
        moviesDetailsErrorMessage.visibility = View.VISIBLE
        moviesDetails.visibility = View.GONE
    }

}
