package com.example.movieapp.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_table")
data class DbGenre(
    @PrimaryKey
    val id: Int,
    val name: String
) 