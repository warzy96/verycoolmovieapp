package com.example.movieapp.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.callback.MovieCallback
import com.example.movieapp.data.callback.PosterCallback
import com.example.movieapp.data.repository.MovieRepositoryProvider
import com.example.movieapp.domain.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.movie_item.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var movieCallback : MovieCallback
    private val viewAdapter by lazy { MovieListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)

        movieRecyclerView.apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        movieRecyclerView.addItemDecoration(
            DividerItemDecoration(movieRecyclerView.context, viewManager.orientation)
        )

        swipeMovieContainer.setOnRefreshListener {
            loadMovies()
        }

        movieCallback = object : MovieCallback {
            override fun onMoviesFetched(movies: List<Movie>) {
                viewAdapter.setData(movies)
                swipeMovieContainer.isRefreshing = false
            }
        }

        loadMovies()
    }

    inner class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
        private var movieList: MutableList<Movie> = mutableListOf()

        inner class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            var mTextView = view.findViewById<TextView>(R.id.movieTitle)
            var poster = view.findViewById<ImageView>(R.id.movieListPoster)
            var rating = view.findViewById<RatingBar>(R.id.movieRating)
            var ratingCount = view.findViewById<TextView>(R.id.movieRatingCount)
            var releaseDate = view.findViewById<TextView>(R.id.movieReleaseDate)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                = MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false))

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            holder.view.setOnClickListener {
                val intent = android.content.Intent(this@MainActivity, MovieDetailsActivity::class.java)
                intent.putExtra("movie", movieList[position])
                startActivity(intent)
            }
            holder.mTextView.text = movieList[position].title
            MovieRepositoryProvider.getRepository().getPoster(movieList[position].posterPath.substring(1),
                object: PosterCallback {
                    override fun onPosterFetched(bitmap: Bitmap) {
                        val dim = resources.getDimension(R.dimen.poster_list_width).toDouble()
                        holder.poster.setImageBitmap(Bitmap.createScaledBitmap(bitmap, dim.toInt(), ((bitmap.height.toDouble() / bitmap.width) * dim).toInt(), false))
                    }
                }
            )
            holder.rating.rating = movieList[position].voteAverage.toFloat() / 2
            holder.ratingCount.text = movieList[position].voteAverage.toString() + "/10 (" + movieList[position].voteCount.toString() + ")"

            val date = SimpleDateFormat("yyyy-MM-dd").parse(movieList[position].releaseDate)
            val format = SimpleDateFormat("dd/MM/yyy")
            holder.releaseDate.text = format.format(date)
        }

        override fun getItemCount() = movieList.size

        fun setData(movieList : List<Movie>) {
            this.movieList = movieList.toMutableList()
            notifyDataSetChanged()
        }
    }

    fun loadMovies() {
        MovieRepositoryProvider.getRepository().getMovies(movieCallback)
    }
}
