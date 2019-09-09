package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM genre_table")
    fun getAll(): List<DbGenre>

    @Query("SELECT * FROM genre_table WHERE genre_id = (:genreId)")
    fun loadById(genreId: Int): DbGenre

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(genres: List<DbGenre>): List<Long>
}