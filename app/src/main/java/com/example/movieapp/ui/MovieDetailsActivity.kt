package com.example.movieapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.model.Genre
import com.example.movieapp.data.model.GenreProvider
import com.example.movieapp.domain.Movie
import com.example.movieapp.ui.utils.MovieUtils
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        private const val MOVIE_ID_EXTRA = "movie"

        @JvmStatic
        fun createIntent(context: Context, movie: Movie) = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(MOVIE_ID_EXTRA, movie)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movie: Movie = intent.getSerializableExtra("movie") as Movie

        MovieUtils.loadPoster(Glide.with(this), movie.posterPath.substring(1), moviePoster)

        movieDetailsTitle.text = movie.title

        if (movie.title != (movie.originalTitle)) {
            movieDetailsTitle.text = movieDetailsTitle.text.toString() + " " + MovieUtils.formatOriginalTitle(movie.originalTitle)
        }

        movieDetailsVote.text = MovieUtils.formatVotes(movie.voteAverage, movie.voteCount)
        movieDetailsOverview.text = movie.overview

        movieDetailsReleaseDate.text = MovieUtils.formatDate(movie.releaseDate)

        if (movie.isAdult) {
            movieDetailsAdult.apply {
                visibility = View.VISIBLE
            }
        }

        GenreProvider.getGenres(object : GenresCallback {
            override fun onGenresFetched(genres: List<Genre>) {
                val genreList = mutableListOf<String>()

                for (id in movie.genreIds) {
                    val genre = genres.filter { g -> g.id?.equals(id) ?: false }.single().name

                    if (genre != null) {
                        genreList.add(genre)
                    }
                }

                movieDetailsGenre.text = genreList.joinToString(", ")
            }

            override fun onError() {
                movieDetailsGenreTxt.apply {
                    visibility = View.GONE
                }
            }
        })

    }

}
