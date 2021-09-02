package com.example.forecastapp.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsTable")
data class NewsEntity(
    @PrimaryKey()
    @ColumnInfo(name = "dt")
    val id: Int,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "clouds")
    val clouds: String? = null,
    @ColumnInfo(name = "wind") val wind: String? = null,
    @ColumnInfo(name = "rain") val rain: String? = null,
    @ColumnInfo(name = "dtTxt") val dtTxt: String? = null,
    @ColumnInfo(name = "weather") val weather: String? = null
) {

}

