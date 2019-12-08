package org.imaginativeworld.oopsnointernet

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

class NoInternetSnackbar private constructor(
    private val activity: Activity,
    private val parent: ViewGroup,
    private val indefinite: Boolean,
    private val noInternetConnectionMessage: String,
    private val snackbarActionText: String,
    private val showActionToDismiss: Boolean,
    private val snackbarDismissActionText: String
) {

    private val TAG = "NoInternetSnackbar"

    private lateinit var snackBar: Snackbar

    private var isDismissed = true

    private var connectivityManager: ConnectivityManager =
        activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    var connectionCallback: ConnectionCallback? = null

    init {
        initViews()
        initReceivers()
    }

    fun initViews() {
        snackBar = Snackbar.make(
            parent,
            noInternetConnectionMessage,
            if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
        )

        if (showActionToDismiss) {

            snackBar.setAction(
                snackbarDismissActionText
            ) {
                // dismiss
            }

        } else {

            snackBar.setAction(
                snackbarActionText
            ) {
                NoInternetUtils.turnOnMobileData(activity)
            }

        }
    }

    private fun initReceivers() {

        updateConnection()

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager.registerDefaultNetworkCallback(
                    getConnectivityManagerCallback()
                )
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                val builder = NetworkRequest.Builder()
                    .addTransportType(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(android.net.NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(android.net.NetworkCapabilities.TRANSPORT_ETHERNET)
                connectivityManager.registerNetworkCallback(
                    builder.build(),
                    getConnectivityManagerCallback()
                )
            }
        }

    }

    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork?.isConnected == true) {
            snackBar.dismiss()

            connectionCallback?.hasActiveConnection(true)
        } else {
            show()

            connectionCallback?.hasActiveConnection(false)
        }
    }

    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network?) {
                    Log.d(TAG, "onAvailable(): ${network.toString()}")

                    dismiss()

                    connectionCallback?.hasActiveConnection(true)
                }

                override fun onLost(network: Network?) {
                    Log.d(TAG, "onLost(): ${network.toString()}")

                    show()

                    connectionCallback?.hasActiveConnection(false)
                }
            }

            return connectivityManagerCallback
        } else {
            throw IllegalAccessError("This should not happened!")
        }
    }

    fun dismiss() {
        if (!isDismissed) {
            isDismissed = true
            snackBar.dismiss()
        }
    }

    fun show() {
        Ping().apply {
            connectionCallback = object : ConnectionCallback {
                override fun hasActiveConnection(hasActiveConnection: Boolean) {
                    if (!hasActiveConnection) {
                        isDismissed = false
                        snackBar.show()
                    }
                }
            }

            execute(activity)
        }
    }

    fun destroy() {
        snackBar.dismiss()

        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    class Builder(
        private val activity: Activity,
        private val parent: ViewGroup
    ) {

        var indefinite = true
        var connectionCallback: ConnectionCallback? = null

        var noInternetConnectionMessage = activity.getString(R.string.no_active_internet_connection)
        var snackbarActionText = activity.getString(R.string.settings)
        var showActionToDismiss = false
        var snackbarDismissActionText = activity.getString(R.string.ok)

        fun build(): NoInternetSnackbar {

            val noInternetSnackbar = NoInternetSnackbar(
                activity,
                parent,
                indefinite,
                noInternetConnectionMessage,
                snackbarActionText,
                showActionToDismiss,
                snackbarDismissActionText
            )

            noInternetSnackbar.connectionCallback = connectionCallback

            return noInternetSnackbar

        }

    }

}