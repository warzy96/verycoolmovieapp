package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface MovieCountryJoinDao {

    @Query(
        "SELECT * FROM production_country_table " +
                "INNER JOIN movie_country_join " +
                "ON production_country_table.iso_code = movie_country_join.iso_code " +
                "WHERE movie_country_join.movie_id = (:movieId)"
    )
    fun getCountriesForMovie(movieId: Int): Single<List<DbProductionCountry>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(genres: List<MovieCountryJoin>): List<Long>
}