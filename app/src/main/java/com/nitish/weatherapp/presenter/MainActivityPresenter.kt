package com.nitish.weatherapp.presenter

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nitish.weatherapp.database.RainfallResponse
import com.nitish.weatherapp.database.TempResponse
import com.nitish.weatherapp.database.TminResponse
import com.nitish.weatherapp.utils.AppController
import com.nitish.weatherapp.utils.CommonUtils
import com.nitish.weatherapp.view.MainActivityView
import org.json.JSONArray

class MainActivityPresenter(private var view: MainActivityView, private var context: Context) {

    private val TAG = javaClass.simpleName
    private val tag_json_arry = "TmaxRequest"
    private val BASE_URL = "https://s3.eu-west-2.amazonaws.com/interview-question-data/metoffice/"

    fun getMaxTemps() {

        val city = view.getCity()
        val url = BASE_URL + "Tmax-$city.json"

        if (CommonUtils.checkInternet(context)) {

            val request = JsonArrayRequest(url, Response.Listener<JSONArray> { response ->

                Log.d(TAG, response.toString())
                val responseList: List<TempResponse> =
                    Gson().fromJson(response.toString(), object : TypeToken<List<TempResponse>>() {}.type)
                view.onTempMaxSuccess(responseList)
            }, Response.ErrorListener { error ->
                VolleyLog.d(TAG, "Error: " + error!!.message)
                view.onError("Error: " + error!!.message, error!!.message!!)
            })

            AppController.getInstance().addToRequestQueue(request)
        } else {
            view.onError("No Internet Connection", "Connection Error")
        }
    }

    fun getMinTemps() {


        val city = view.getCity()
        val url = BASE_URL + "Tmin-$city.json"

        if (CommonUtils.checkInternet(context)) {


            val request = JsonArrayRequest(url, Response.Listener<JSONArray> { response ->

                Log.d(TAG, response.toString())
                val responseList: List<TminResponse> =
                    Gson().fromJson(response.toString(), object : TypeToken<List<TminResponse>>() {}.type)
                view.onTempMinSuccess(responseList)


                /*responseList
                    .map { questions: List<TminResponse> ->
                        createAnswerViewModels(answers, questions) }*/

            }, Response.ErrorListener { error ->
                VolleyLog.d(TAG, "Error: " + error!!.message)
                view.onError("Error: " + error!!.message, error!!.message!!)
            })

            AppController.getInstance().addToRequestQueue(request)
        } else {
            view.onError("No Internet Connection", "Connection Error")
        }
    }


    fun getRainfall() {


        var url: String? = null
        val city = view.getCity()
        url = BASE_URL + "Rainfall-$city.json"
        if (CommonUtils.checkInternet(context)) {

            val request = JsonArrayRequest(url, Response.Listener<JSONArray> { response ->

                Log.d(TAG, response.toString())
                val responseList: List<RainfallResponse> =
                    Gson().fromJson(response.toString(), object : TypeToken<List<RainfallResponse>>() {}.type)
                view.onRainfallSuccess(responseList)
            }, Response.ErrorListener { error ->
                VolleyLog.d(TAG, "Error: " + error!!.message)
                view.onError("Error: " + error!!.message, error!!.message!!)
            })

            AppController.getInstance().addToRequestQueue(request)

        } else {
            view.onError("No Internet Connection", "Connection Error")
        }
    }
}