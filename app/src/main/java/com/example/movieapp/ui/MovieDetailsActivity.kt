package com.example.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.domain.Movie
import android.graphics.Bitmap
import kotlinx.android.synthetic.main.activity_movie_details.*
import android.view.View
import com.example.movieapp.R
import com.example.movieapp.data.callback.GenresCallback
import com.example.movieapp.data.callback.PosterCallback
import com.example.movieapp.data.model.Genre
import com.example.movieapp.data.model.GenreProvider
import com.example.movieapp.data.repository.MovieRepositoryProvider

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movie : Movie = intent.getSerializableExtra("movie") as Movie

        val posterCallback = object : PosterCallback {
            override fun onPosterFetched(bitmap: Bitmap) {
                val dim = resources.getDimension(R.dimen.poster_width).toDouble()
                moviePoster.setImageBitmap(Bitmap.createScaledBitmap(bitmap, dim.toInt(), ((bitmap.height.toDouble() / bitmap.width) * dim).toInt(), false))
            }
        }

        MovieRepositoryProvider.getRepository().getPoster(movie.posterPath.substring(1), posterCallback)

        movieDetailsTitle.text = movie.title

        if(!movie.title.equals(movie.originalTitle)) {
            movieDetailsTitle.text = movieDetailsTitle.text.toString() + " (" + movie.originalTitle + ")"
        }

        movieDetailsVote.text = movie.voteAverage.toString() + "/10 (" + movie.voteCount + ")"
        movieDetailsOverview.text = movie.overview

        movieDetailsReleaseDate.text = movie.releaseDate

        if(movie.isAdult) {
            movieDetailsAdult.visibility = View.VISIBLE
        }

        GenreProvider.getGenres(object: GenresCallback {
            override fun onGenresFetched(genres: List<Genre>) {
                var first = true

                for(id in movie.genreIds) {
                    if (first) {
                        first = false
                    } else {
                        movieDetailsGenre.text = movieDetailsGenre.text.toString() + ", "
                    }
                    movieDetailsGenre.text = movieDetailsGenre.text.toString() + genres.filter { g -> g.id?.equals(id) ?: false }.single().name
                }
            }
        })

    }

}
