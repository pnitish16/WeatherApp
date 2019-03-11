package com.nitish.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import com.nitish.weatherapp.adapter.SpinnerAdapter
import com.nitish.weatherapp.adapter.TempRecyclerViewAdapter
import com.nitish.weatherapp.database.DatabaseClient
import com.nitish.weatherapp.database.RainfallResponse
import com.nitish.weatherapp.database.TempResponse
import com.nitish.weatherapp.database.TminResponse
import com.nitish.weatherapp.presenter.MainActivityPresenter
import com.nitish.weatherapp.view.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityView {

    private val cityList = listOf("UK", "England", "Scotland", "Wales")
    private lateinit var context: Context
    private lateinit var cityAdapter: SpinnerAdapter
    private var selectedCity: String? = null
    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        setSupportActionBar(mainToolbar)
        if (supportActionBar != null) {
            supportActionBar!!.title = getString(R.string.app_name)
        }

        cityAdapter = SpinnerAdapter(context, cityList)
        spCity.adapter = cityAdapter
        spCity.onItemSelectedListener = CitySpinnerAdapter()

        selectedCity = cityList[0]

        //checking if the local data for city present
        pbMain.visibility = View.VISIBLE
        tvEmpty.visibility = View.GONE
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        CheckDatabase().execute()
    }

    //region ITEMSELECTEDLISTENER FOR THE CITY SPINNER
    inner class CitySpinnerAdapter : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            selectedCity = cityList[position]
            CheckDatabase().execute()
        }
    }

    override fun onResume() {
        super.onResume()

        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }


    override fun getCity(): String {
        return selectedCity.apply { } ?: ""
    }

    //region Presenter Callbacks
    override fun onTempMaxSuccess(list: List<TempResponse>) {
        SaveTemps().execute(list)
    }

    override fun onTempMinSuccess(tempList: List<TminResponse>) {
        SaveTempsMin().execute(tempList)
    }

    override fun onRainfallSuccess(tempList: List<RainfallResponse>) {
        SaveRainfall().execute(tempList)
    }

    override fun onError(error: String, errorMessage: String) {
        pbMain.visibility = View.GONE
        rvTmaxList.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        tvEmpty.visibility = View.VISIBLE
        Log.d("error", error)
    }
    //endregion


    //saving the max temp  in the local database
    @SuppressLint("StaticFieldLeak")
    inner class SaveTemps : AsyncTask<List<TempResponse>, Void, Void>() {
        override fun doInBackground(vararg params: List<TempResponse>): Void? {

            val database = DatabaseClient.getInstance(applicationContext).appDatabase

            for (i in 0 until params[0].size) {
                database!!.tmaxDao().insert(
                    TempResponse(
                        0,
                        selectedCity!!,
                        params[0][i].month,
                        params[0][i].year,
                        params[0][i].value
                    )
                )
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            presenter.getMinTemps()
        }
    }

    //saving the min temp  in the local database
    @SuppressLint("StaticFieldLeak")
    inner class SaveTempsMin : AsyncTask<List<TminResponse>, Void, Void>() {
        override fun doInBackground(vararg params: List<TminResponse>): Void? {

            val database = DatabaseClient.getInstance(applicationContext).appDatabase

            for (i in 0 until params[0].size) {
                database!!.tminDao().insert(
                    TminResponse(
                        0,
                        selectedCity!!,
                        params[0][i].month,
                        params[0][i].year,
                        params[0][i].value
                    )
                )
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            presenter.getRainfall()
        }
    }

    //saving the rainfall in the local database
    @SuppressLint("StaticFieldLeak")
    inner class SaveRainfall : AsyncTask<List<RainfallResponse>, Void, Void>() {
        override fun doInBackground(vararg params: List<RainfallResponse>): Void? {

            val database = DatabaseClient.getInstance(applicationContext).appDatabase

            for (i in 0 until params[0].size) {
                database!!.rainfallDao().insert(
                    RainfallResponse(
                        0,
                        selectedCity!!,
                        params[0][i].month,
                        params[0][i].year,
                        params[0][i].value
                    )
                )
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            GetTemps().execute()
        }
    }

    //getting the min , max temps and rainfall from the database
    @SuppressLint("StaticFieldLeak")
    inner class GetTemps : AsyncTask<Void, Void, Unit>() {

        var tempList: List<TempResponse>? = null
        var tminList: List<TminResponse>? = null
        var rainfallList: List<RainfallResponse>? = null

        override fun doInBackground(vararg params: Void?) {
            val database = DatabaseClient.getInstance(applicationContext).appDatabase
            tempList = database!!.tmaxDao().getAllTempsForCity(selectedCity!!)
            tminList = database.tminDao().getAllTempsForCity(selectedCity!!)
            rainfallList = database.rainfallDao().getRainfallForCity(selectedCity!!)

        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)

            pbMain.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            rvTmaxList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            val tempList1 = tempList.apply { } ?: return
            val tminList1 = tminList.apply { } ?: return
            val rainfallList1 = rainfallList.apply { } ?: return

            rvTmaxList.visibility = View.VISIBLE
            tvEmpty.visibility = View.GONE
            rvTmaxList.adapter = TempRecyclerViewAdapter(tempList1, tminList1, rainfallList1)

        }
    }


    // for checking if local database for the city exits
    @SuppressLint("StaticFieldLeak")
    inner class CheckDatabase : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            val database = DatabaseClient.getInstance(applicationContext).appDatabase
            return !database.tmaxDao().getAllTempsForCity(selectedCity!!).isNotEmpty()
        }

        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)

            if (result) {
                pbMain.visibility = View.VISIBLE
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                presenter = MainActivityPresenter(this@MainActivity, context)
                presenter.getMaxTemps()
            } else {
                GetTemps().execute()
            }
        }
    }
    //endregion
}


