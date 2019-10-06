package com.example.movieapp.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "production_country_table")
data class DbProductionCountry(

    @PrimaryKey
    @ColumnInfo(name = "iso_code")
    val isoCode: String,

    val name: String
)