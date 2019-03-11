package com.nitish.weatherapp.view

import com.nitish.weatherapp.database.RainfallResponse
import com.nitish.weatherapp.database.TempResponse
import com.nitish.weatherapp.database.TminResponse

interface MainActivityView {

    fun getCity(): String
    fun onTempMaxSuccess(tempList: List<TempResponse>)
    fun onTempMinSuccess(tempList: List<TminResponse>)
    fun onRainfallSuccess(tempList: List<RainfallResponse>)
    fun onError(error: String, errorMessage: String)
}