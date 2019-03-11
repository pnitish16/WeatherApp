package com.nitish.weatherapp.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface TmaxDao {

    @get:Query("SELECT * from tmax_table ORDER BY year ASC")
    val allTemps: List<TempResponse>

    @Query("SELECT * from tmax_table WHERE cityName LIKE:cityName ORDER BY year DESC")
    fun getAllTempsForCity(cityName : String): List<TempResponse>

    @Insert()
    fun insert(word: TempResponse)

    @Query("DELETE FROM tmax_table")
    fun deleteAll()

}