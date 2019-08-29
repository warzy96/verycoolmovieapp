package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.movieapp.domain.DbProductionCountry

@Dao
interface ProductionCountryDao {
    @Query("SELECT * FROM production_country_table")
    fun getAll(): List<DbProductionCountry>

    @Query("SELECT * FROM production_country_table WHERE iso_code = (:isoCode)")
    fun loadByIsoCode(isoCode: String): DbMovieDetails
}