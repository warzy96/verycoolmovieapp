package com.example.movieapp.data.dao

import androidx.room.*
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAll(): Single<List<DbMovie>>

    @Query("SELECT * FROM favorite_movies WHERE movie_id = (:movieId)")
    fun loadById(movieId: Int): Single<DbMovie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movies: List<DbMovie>): List<Long>

    @Delete
    fun delete(movie: DbMovie): Int
}