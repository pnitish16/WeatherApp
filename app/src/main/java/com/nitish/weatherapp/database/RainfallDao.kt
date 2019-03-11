package com.nitish.weatherapp.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface RainfallDao {

    @get:Query("SELECT * from rainfall_table ORDER BY year DESC")
    val allRainfall: List<RainfallResponse>

    @Query("SELECT * from rainfall_table WHERE cityName LIKE:cityName ORDER BY year DESC")
    fun getRainfallForCity(cityName : String): List<RainfallResponse>

    @Insert()
    fun insert(word: RainfallResponse)

    @Query("DELETE FROM rainfall_table")
    fun deleteAll()

}