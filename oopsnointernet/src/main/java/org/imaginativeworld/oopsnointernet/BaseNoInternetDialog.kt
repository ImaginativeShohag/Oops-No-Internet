package org.imaginativeworld.oopsnointernet

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseNoInternetDialog(
    private val activity: Activity,
    lifecycle: Lifecycle,
    private val dialogProperties: BaseDialogProperties
) : Dialog(activity), LifecycleObserver {

    private val TAG = "BaseNoInternetDialog"

    private val dialogScope = CoroutineScope(Dispatchers.IO)
    private var currentJob: Job? = null

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    private var connectionCallback: ConnectionCallback? = dialogProperties.connectionCallback

    init {
        Log.d(TAG, "init")

        // Add to the lifecycle owner observer list.
        @Suppress("LeakingThis")
        lifecycle.addObserver(this)
    }

    private fun initReceivers() {
        Log.d(TAG, "initReceivers")

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
        Log.d(TAG, "updateConnection")

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork?.isConnected == true) {
            dismiss()

            connectionCallback?.hasActiveConnection(true)
        } else {
            showDialog()

            connectionCallback?.hasActiveConnection(false)
        }
    }

    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        Log.d(TAG, "getConnectivityManagerCallback")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Log.d(TAG, "onAvailable(): $network")

                    dismiss()

                    connectionCallback?.hasActiveConnection(true)
                }

                override fun onLost(network: Network) {
                    Log.d(TAG, "onLost(): $network")

                    showDialog()

                    connectionCallback?.hasActiveConnection(false)
                }
            }

            return connectivityManagerCallback
        } else {
            throw IllegalAccessError("This should not happened")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setLayout()
        initView()

        initProperties()
        initMainWindow()
    }

    private fun initProperties() {
        Log.d(TAG, "initProperties")

        setCancelable(dialogProperties.cancelable)
    }

    private fun initMainWindow() {
        Log.d(TAG, "initMainWindow")

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    override fun show() {
        Log.d(TAG, "show")

        if (!activity.isFinishing) {
            super.show()

            onShow(NoInternetUtils.isAirplaneModeOn(activity))
        }
    }

    /**
     * Check the internet connectivity and show the dialog if no internet.
     */
    fun showDialog() {
        Log.d(TAG, "showDialog")

        currentJob = dialogScope.launch {
            val hasActiveConnection = NoInternetUtils.isConnectedToInternet(context)
                    && NoInternetUtils.hasActiveInternetConnection()

            if (!hasActiveConnection) {
                launch(Dispatchers.Main) {
                    show()
                }
            }
        }
    }

    /**
     * Destroy the dialog.
     */
    fun destroy() {
        Log.d(TAG, "destroy")

        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)

        currentJob?.cancel()

        onDestroy()

        dismiss()
    }


    /**
     * Start listening.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun start() {
        Log.d(TAG, "start")

        initReceivers()
    }

    /**
     * Stop listening.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stop() {
        Log.d(TAG, "stop")

        destroy()
    }

    /**
     * Set content view here.
     */
    internal abstract fun setLayout()

    /**
     * Initialize view elements.
     */
    internal abstract fun initView()

    /**
     * Thing to do after dialog showed, like start the animations etc.
     */
    internal abstract fun onShow(isAirplaneModeOn: Boolean)

    /**
     * Thing to do on destroy dialog.
     */
    internal abstract fun onDestroy()

}