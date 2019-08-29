package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface MovieDetailsDao {
    @Query("SELECT * FROM movie_details_table")
    fun getAll(): List<DbMovieDetails>

    @Query("SELECT * FROM movie_details_table WHERE movie_id = (:userId)")
    fun loadById(userId: Int): Single<DbMovieDetails>

    @Insert
    fun insert(movieDetails: DbMovieDetails): Single<Long>
}