package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductionCountryDao {

    @Query("SELECT * FROM production_country_table")
    fun getAll(): List<DbProductionCountry>

    @Query("SELECT * FROM production_country_table WHERE iso_code = (:isoCode)")
    fun loadByIsoCode(isoCode: String): DbProductionCountry

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(genres: List<DbProductionCountry>): List<Long>
}