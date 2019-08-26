package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.domain.Movie
import com.example.movieapp.ui.MovieApplication.Companion.imageLoader
import com.example.movieapp.ui.listener.MovieClickListener
import com.example.movieapp.ui.utils.MovieUtils
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private val movieClickListener: MovieClickListener,
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(layoutInflater.inflate(R.layout.movie_item, parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.updateValues(movies[position], movieClickListener)
    }

    override fun getItemCount() = movies.size

    fun setData(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun updateValues(movie: Movie, movieClickListener: MovieClickListener) {
            with(view) {
                movieTitle.text = movie.title
                imageLoader.loadImage(movie.posterPath, movieListPoster)
                movieRating.rating = movie.voteAverage.toFloat()
                movieRatingCount.text = MovieUtils.formatVotes(movie.voteAverage, movie.voteCount)
                movieReleaseDate.text = MovieUtils.formatDate(movie.releaseDate)
                setOnClickListener {
                    movieClickListener.onMovieClicked(movie)
                }
            }
        }
    }
}