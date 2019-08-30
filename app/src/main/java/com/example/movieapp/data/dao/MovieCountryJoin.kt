package com.example.movieapp.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "movie_country_join",
    primaryKeys = ["movie_id", "iso_code"],
    foreignKeys = arrayOf(
        ForeignKey(
            entity = DbMovieDetails::class,
            parentColumns = arrayOf("movie_id"),
            childColumns = arrayOf("movie_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DbProductionCountry::class,
            parentColumns = arrayOf("iso_code"),
            childColumns = arrayOf("iso_code"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
class MovieCountryJoin(

    @ColumnInfo(name = "movie_id")
    val movieId: Int,

    @ColumnInfo(name = "iso_code")
    val isoCode: String
)

