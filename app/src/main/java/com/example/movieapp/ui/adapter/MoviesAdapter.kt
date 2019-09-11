package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.util.ImageLoader
import com.example.movieapp.ui.view.model.MovieViewModel
import com.example.movieapp.ui.listener.FavoriteClickListener
import com.example.movieapp.ui.listener.MovieClickListener
import com.example.movieapp.ui.utils.MovieUtils
import kotlinx.android.synthetic.main.movie_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoviesAdapter(
    private val movieClickListener: MovieClickListener,
    private val favoriteClickListener: FavoriteClickListener,
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    companion object {
        private const val FADE_DURATION = 1000L
        private const val ALPHA_ANIM_START_VALUE = 0.0f
        private const val ALPHA_ANIM_END_VALUE = 1.0f
    }

    private val movies: MutableList<MovieViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(layoutInflater.inflate(R.layout.movie_item, parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.updateValues(movies[position], movieClickListener, favoriteClickListener)
        val animationSet = AnimationSet(false)
        animationSet.addAnimation(AnimationUtils.loadAnimation(holder.view.context, R.anim.abc_slide_in_bottom))
        animationSet.addAnimation(holder.getFadeAnimation())
        holder.view.startAnimation(animationSet)
    }

    override fun getItemCount() = movies.size

    fun setData(movies: List<MovieViewModel>) {
        val size = itemCount

        this.movies.clear()
        notifyItemRangeRemoved(0, size)
        this.movies.addAll(movies)
        notifyItemRangeInserted(0, itemCount)
    }

    fun addData(movies: List<MovieViewModel>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(itemCount - movies.size, itemCount)
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view), KoinComponent {

        private val imageLoader: ImageLoader by inject()

        fun updateValues(movie: MovieViewModel, movieClickListener: MovieClickListener, favoriteClickListener: FavoriteClickListener) {
            with(view) {
                movieTitle.text = movie.title
                imageLoader.loadImage(movie.posterPath, movieListPoster)
                movieRating.rating = movie.voteAverage.toFloat()
                movieRatingCount.text = MovieUtils.formatVotes(movie.voteAverage, movie.voteCount)
                if (!movie.releaseDate.isBlank()) {
                    movieReleaseDate.text = MovieUtils.formatDate(movie.releaseDate)
                }
                setOnClickListener {
                    movieClickListener.onMovieClicked(movie)
                }
                favoriteIcon.backgroundTintList =
                    ContextCompat.getColorStateList(favoriteIcon.context, if (movie.favorite) R.color.favoriteColor else R.color.notFavoriteColor)
                favoriteIcon.setOnClickListener {
                    val on = favoriteIcon.isChecked

                    if (on) {
                        favoriteIcon.backgroundTintList = ContextCompat.getColorStateList(favoriteIcon.context, R.color.favoriteColor)
                        movie.favorite = true
                        favoriteClickListener.onToggleOn(movie)
                    } else {
                        favoriteIcon.backgroundTintList = ContextCompat.getColorStateList(favoriteIcon.context, R.color.notFavoriteColor)
                        movie.favorite = false
                        favoriteClickListener.onToggleOff(movie)
                    }
                }
            }
        }

        fun getFadeAnimation(): Animation {
            val anim = AlphaAnimation(ALPHA_ANIM_START_VALUE, ALPHA_ANIM_END_VALUE)
            anim.duration = FADE_DURATION
            return anim
        }
    }
}