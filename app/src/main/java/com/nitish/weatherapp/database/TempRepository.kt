package com.nitish.weatherapp.database

import android.content.Context
import android.os.AsyncTask

class TempRepository {

    private lateinit var tmaxDao: TmaxDao
    private lateinit var tmaxTemps: List<TempResponse>

    fun TempRepository(context: Context) {
        val db = DatabaseClient(context).appDatabase
        tmaxDao = db!!.tmaxDao()
        tmaxTemps = tmaxDao.allTemps
    }

    fun getAllWords(): List<TempResponse> {
        return tmaxTemps
    }


    fun insertAll(list: List<TempResponse>) {
        insertAsyncTaskAll(tmaxDao).execute(list)
    }

    fun insert(tmaxResponse: TempResponse) {
        insertAsyncTask(tmaxDao).execute(tmaxResponse)
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: TmaxDao) :
        AsyncTask<TempResponse, Void, Void>() {

        override fun doInBackground(vararg params: TempResponse): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class insertAsyncTaskAll internal constructor(private val mAsyncTaskDao: TmaxDao) :
        AsyncTask<List<TempResponse>, Void, Void>() {

        override fun doInBackground(vararg params: List<TempResponse>): Void? {
            for (i in params[0].indices) {
                mAsyncTaskDao.insert(params[0][i])
            }
            return null
        }
    }

}