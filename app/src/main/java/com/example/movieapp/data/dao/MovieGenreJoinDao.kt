package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface MovieGenreJoinDao {

    @Query(
        "SELECT * FROM genre_table " +
                "INNER JOIN movie_genre_join " +
                "ON genre_table.genre_id = movie_genre_join.genre_id " +
                "WHERE movie_genre_join.movie_id = (:movieId)"
    )
    fun getGenresForMovie(movieId: Int): Single<List<DbGenre>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(genres: List<MovieGenreJoin>): List<Long>
}