package org.imaginativeworld.oopsnointernet

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object NoInternetUtils {

    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork?.isConnected == true
    }

    fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.System.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) != 0
    }

    fun hasActiveInternetConnection(): Boolean {
        try {
            val urlConnection =
                URL("https://www.google.com").openConnection() as HttpURLConnection

            urlConnection.setRequestProperty("User-Agent", "Test")
            urlConnection.setRequestProperty("Connection", "close")
            urlConnection.connectTimeout = 1500
            urlConnection.connect()

            return urlConnection.responseCode == 200
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    fun turnOnMobileData(context: Context) {
        context.startActivity(Intent(Settings.ACTION_SETTINGS))
    }

    fun turnOnWifi(context: Context) {
        context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }

    fun turnOffAirplaneMode(context: Context) {
        context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }

}