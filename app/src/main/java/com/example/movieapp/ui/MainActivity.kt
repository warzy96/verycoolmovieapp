package com.example.movieapp.ui

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movieapp.R
import com.example.movieapp.data.MovieService
import com.example.movieapp.data.MovieServiceImpl
import com.example.movieapp.domain.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var getMoviesButton : Button
    private var movieService : MovieServiceImpl = MovieServiceImpl()

    var movieList : MutableList<Movie> = mutableListOf()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var swipeContainer : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMoviesButton = findViewById(R.id.get_movies_button)

        getMoviesButton.setOnClickListener {
            LoadMovies().execute(movieService)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = MovieListAdapter(movieList)

        recyclerView = findViewById<RecyclerView>(R.id.movie_recycler_view).apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.getContext(),
                (viewManager as LinearLayoutManager).orientation)
        )

        swipeContainer = findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            LoadMovies().execute(movieService)
        }
    }

    class MovieListAdapter(private val myDataset: List<Movie>) :
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

        class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            var mTextView = view.findViewById<TextView>(R.id.movie_title)
        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)

            return MovieViewHolder(view)
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            holder.mTextView.text = myDataset[position].title
        }

        override fun getItemCount() = myDataset.size
    }


    inner class LoadMovies : AsyncTask<MovieService, Void, List<Movie>?>() {
        override fun doInBackground(vararg p0: MovieService?) : List<Movie>? {
            return p0[0]?.getMovies()
        }

        override fun onPostExecute(movies: List<Movie>?) {
            movieList.clear()
            movieList.addAll(movies!!)

            viewAdapter.notifyDataSetChanged()
            swipeContainer.isRefreshing = false
        }

    }

}
