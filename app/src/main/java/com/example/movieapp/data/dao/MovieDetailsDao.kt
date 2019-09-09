package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM movie_details_table")
    fun getAll(): Single<List<DbMovieDetails>>

    @Query("SELECT * FROM movie_details_table WHERE movie_id = (:movieId)")
    fun loadById(movieId: Int): Single<DbMovieDetails>

    @Insert
    fun insert(movieDetails: DbMovieDetails): Long

    @Query("DELETE FROM movie_details_table WHERE movie_id = :movieId")
    fun deleteById(movieId: Int): Int
}