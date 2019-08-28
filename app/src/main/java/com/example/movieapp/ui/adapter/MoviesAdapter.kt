package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.ImageLoader
import com.example.movieapp.data.view.model.MovieViewModel
import com.example.movieapp.ui.listener.MovieClickListener
import com.example.movieapp.ui.utils.MovieUtils
import kotlinx.android.synthetic.main.movie_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoviesAdapter(
    private val movieClickListener: MovieClickListener,
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    companion object {
        private const val FADE_DURATION = 1000L
    }

    private val movies: MutableList<MovieViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(layoutInflater.inflate(R.layout.movie_item, parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.updateValues(movies[position], movieClickListener)
        setFadeAnimation(holder.itemView)
    }

    override fun getItemCount() = movies.size

    fun setData(movies: List<MovieViewModel>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun addData(movies: List<MovieViewModel>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(itemCount - movies.size, itemCount)
    }

    fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION
        view.startAnimation(anim)
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view), KoinComponent {

        private val imageLoader: ImageLoader by inject()

        fun updateValues(movie: MovieViewModel, movieClickListener: MovieClickListener) {
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