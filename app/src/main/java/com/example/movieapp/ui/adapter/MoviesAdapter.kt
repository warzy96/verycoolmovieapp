package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.movieapp.R
import com.example.movieapp.data.view.model.ViewMovie
import com.example.movieapp.ui.listener.MovieClickListener
import com.example.movieapp.ui.utils.MovieUtils
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private val movieClickListener: MovieClickListener,
    val layoutInflater: LayoutInflater,
    val requestManager: RequestManager
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private var movies: MutableList<ViewMovie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(layoutInflater.inflate(R.layout.movie_item, parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.setOnClickListener {
            movieClickListener.onMovieClicked(movies[position])
        }

        holder.title.text = movies[position].title

        MovieUtils.loadPoster(requestManager, movies[position].posterPath.substring(1), holder.poster)

        holder.rating.rating = movies[position].voteAverage.toFloat() / 2
        holder.ratingCount.text = MovieUtils.formatVotes(movies[position].voteAverage, movies[position].voteCount)

        holder.releaseDate.text = MovieUtils.formatDate(movies[position].releaseDate)
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var title = view.movieTitle
        var poster = view.movieListPoster
        var rating = view.movieRating
        var ratingCount = view.movieRatingCount
        var releaseDate = view.movieReleaseDate
    }

    fun setData(movieList: List<ViewMovie>) {
        this.movies = movieList.toMutableList()
        notifyDataSetChanged()
    }
}