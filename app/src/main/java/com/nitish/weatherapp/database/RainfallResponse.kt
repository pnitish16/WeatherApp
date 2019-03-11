package com.nitish.weatherapp.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "rainfall_table")
data class RainfallResponse(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var cityName: String = "",
    var month: Int = 0,
    var year: Int = 0,
    var value: Double = 0.toDouble()
)
