package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre_table")
    fun getAll(): List<DbGenre>

    @Query("SELECT * FROM genre_table WHERE id = (:genreId)")
    fun loadById(genreId: Int): DbMovieDetails
}