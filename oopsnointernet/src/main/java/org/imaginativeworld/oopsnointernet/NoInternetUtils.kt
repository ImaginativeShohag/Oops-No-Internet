package org.imaginativeworld.oopsnointernet

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.provider.Settings
import android.widget.Toast
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object NoInternetUtils {

    /**
     * Check if the device is connected with the Internet.
     */
    @JvmStatic
    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return activeNetwork?.isConnected == true
    }

    /**
     * Check if the device is in airplane mode.
     */
    @JvmStatic
    fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.System.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) != 0
    }

    /**
     * Ping google.com to check if the internet connection is active.
     * It must be called from a background thread.
     */
    @JvmStatic
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

    /**
     * Open the system settings.
     */
    @JvmStatic
    fun turnOnMobileData(context: Context) {
        try {
            context.startActivity(Intent(Settings.ACTION_SETTINGS))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "It cannot open settings!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Open the wifi settings.
     */
    @JvmStatic
    fun turnOnWifi(context: Context) {
        try {
            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "It cannot open settings!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Open the airplane mode settings.
     */
    @JvmStatic
    fun turnOffAirplaneMode(context: Context) {
        try {
            context.startActivity(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "It cannot open settings!", Toast.LENGTH_LONG).show()
        }
    }

}