package com.nitish.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.preference.PreferenceManager

object CommonUtils {

    fun defaultPrefs(context: Context): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(context)

    fun checkInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun setMyStringPref(context: Context, key: String, value: String) { // set
        defaultPrefs(context).edit().putString(key, value).commit()
    }

    fun getMyStringPref(context: Context, key: String): String { // get
        // preference
        // value
        return defaultPrefs(context).getString(key, "")
    }

    fun setMyIntPref(context: Context, key: String, value: Int) { // set
        defaultPrefs(context).edit().putInt(key, value).commit()
    }

    fun getMyIntPref(context: Context, key: String): Int { // get
        // preference
        // value
        return defaultPrefs(context).getInt(key, 0)
    }

    fun setMyBooleanPref(context: Context, key: String, value: Boolean) { // set
        defaultPrefs(context).edit().putBoolean(key, value).commit()
    }

    fun getMyBooleanPref(context: Context, key: String): Boolean { // get
        // preference
        // value
        return defaultPrefs(context).getBoolean(key, false)
    }

    fun removeMyPref(context: Context, key: String) { // remove
        // preference
        // value
        defaultPrefs(context).edit().remove(key).commit()
    }


}