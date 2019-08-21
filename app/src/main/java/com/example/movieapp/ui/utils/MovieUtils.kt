package com.example.movieapp.ui.utils

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.example.movieapp.data.api.PosterLoader
import java.text.SimpleDateFormat

class MovieUtils {
    companion object {
        private const val ORIGINAL_TITLE_FORMAT = "(%s)"
        private const val VOTE_FORMAT = "%.1f/10"
        private const val VOTE_COUNT_FORMAT = "(%d)"

        private const val API_DATE_FORMAT = "yyyy-MM-dd"
        private const val MOVIE_DATE_FORMAT = "dd/MM/yyyy"

        @JvmStatic
        fun formatDate(date: String): String = SimpleDateFormat(MOVIE_DATE_FORMAT).format(SimpleDateFormat(API_DATE_FORMAT).parse(date))

        @JvmStatic
        fun formatVotes(voteAverage: Double, voteCount: Int): String =
            String.format(VOTE_FORMAT, voteAverage) + " " + String.format(VOTE_COUNT_FORMAT, voteCount)

        @JvmStatic
        fun formatOriginalTitle(originalTitle: String): String = String.format(ORIGINAL_TITLE_FORMAT, originalTitle)

        @JvmStatic
        fun loadPoster(requestManager: RequestManager, path: String, imageView: ImageView) = PosterLoader.loadImage(requestManager, path, imageView)
    }
}