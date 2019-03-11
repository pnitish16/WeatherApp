package com.nitish.weatherapp.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface TminDao {

    @get:Query("SELECT * from tmin_table ORDER BY year DESC")
    val allTemps: List<TminResponse>

    @Query("SELECT * from tmin_table WHERE cityName LIKE:cityName ORDER BY year DESC")
    fun getAllTempsForCity(cityName : String): List<TminResponse>

    @Insert()
    fun insert(word: TminResponse)

    @Query("DELETE FROM tmin_table")
    fun deleteAll()

}