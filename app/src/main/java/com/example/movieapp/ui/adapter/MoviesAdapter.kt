package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.view.model.ViewMovie
import com.example.movieapp.ui.MovieApplication.Companion.dependencyInjector
import com.example.movieapp.ui.listener.MovieClickListener
import com.example.movieapp.ui.utils.MovieUtils
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private val movieClickListener: MovieClickListener,
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val movies: MutableList<ViewMovie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(layoutInflater.inflate(R.layout.movie_item, parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.setOnClickListener {
            movieClickListener.onMovieClicked(movies[position])
        }

        holder.updateValues(movies[position])
    }

    override fun getItemCount() = movies.size

    fun setData(movies: List<ViewMovie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var title = view.movieTitle
        private var poster = view.movieListPoster
        private var rating = view.movieRating
        private var ratingCount = view.movieRatingCount
        private var releaseDate = view.movieReleaseDate

        fun updateValues(movie: ViewMovie) {
            title.text = movie.title

            dependencyInjector.getImageLoader().loadPoster(movie.posterPath.substring(1), poster)

            rating.rating = movie.voteAverage.toFloat() / 2
            ratingCount.text = MovieUtils.formatVotes(movie.voteAverage, movie.voteCount)

            releaseDate.text = MovieUtils.formatDate(movie.releaseDate)
        }
    }
}