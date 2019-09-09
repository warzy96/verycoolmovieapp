package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface GenreDao {

    @Query("SELECT * FROM genre_table")
    fun getAll(): Single<List<DbGenre>>

    @Query("SELECT * FROM genre_table WHERE genre_id = (:genreId)")
    fun loadById(genreId: Int): Single<DbGenre>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(genres: List<DbGenre>): List<Long>
}