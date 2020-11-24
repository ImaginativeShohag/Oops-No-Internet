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
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

// todo #1 made a width fix function
// todo #2 stop animation onStop
class CustomNoInternetDialog private constructor(
    private val activity: Activity,
    private val cancelable: Boolean,

    @LayoutRes private val layoutResourceId: Int,
    private val customDialogCallback: CustomDialogCallback
) : Dialog(activity) {

    private val TAG = "CustomNoInternetDialog"

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    private var dialogContentView: View? = null

    var connectionCallback: ConnectionCallback? = null

    init {
        initReceivers()
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
            dismiss()

            connectionCallback?.hasActiveConnection(true)
        } else {
            showDialog()

            connectionCallback?.hasActiveConnection(false)
        }
    }

    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Log.d(TAG, "onAvailable(): ${network.toString()}")

                    dismiss()

                    connectionCallback?.hasActiveConnection(true)
                }

                override fun onLost(network: Network) {
                    Log.d(TAG, "onLost(): ${network.toString()}")

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
        Log.e(TAG, "onCreate")

        val view = layoutInflater.inflate(layoutResourceId, null)

        setContentView(view)

        dialogContentView = view

        initProperties()
        initMainWindow()
        initViews()

        customDialogCallback.initDialogCallback(
            this,
            dialogContentView,
            NoInternetUtils.isAirplaneModeOn(activity)
        )
    }

    private fun initProperties() {
        setCancelable(cancelable)
    }

    private fun initMainWindow() {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initViews() {
        // Check if the dialog width is bigger then the screen width!
        val displayMetrics = DisplayMetrics()
        window?.apply {
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val widthDp = displayMetrics.widthPixels.toFloat().toDp(context)

            Log.d(TAG, "width: $widthDp")

            if (widthDp < 392) { // 360dp width + 16dp*2 margin
                val mainContainer = dialogContentView?.findViewById<View>(R.id.main_container)

                mainContainer?.layoutParams = FrameLayout.LayoutParams(
                    (widthDp - 32).toPx(context),
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

    override fun show() {
        Log.e(TAG, "show")

        if (!activity.isFinishing) {
            super.show()
        }
    }

    fun showDialog() {
        Log.e(TAG, "showDialog")

        Ping().apply {
            connectionCallback = object : ConnectionCallback {
                override fun hasActiveConnection(hasActiveConnection: Boolean) {
                    Log.e(TAG, "Ping(): hasActiveConnection")

                    if (!hasActiveConnection && !activity.isFinishing) {
                        Log.e(
                            TAG,
                            "Ping(): hasActiveConnection: !hasActiveConnection && !activity.isFinishing"
                        )

                        customDialogCallback.onShowCallback(
                            this@CustomNoInternetDialog //,
//                            dialogContentView,
//                            NoInternetUtils.isAirplaneModeOn(activity)
                        )

                        show()

                    }
                }
            }

            execute(context)
        }

    }

    fun destroy() {
        Log.e(TAG, "destroy")

        customDialogCallback.onDestroyCallback(
            this //,
            //dialogContentView
        )

        dismiss()

        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    class Builder(
        private val activity: Activity,
        @LayoutRes val layoutResourceId: Int
    ) {

        var cancelable = false
        var connectionCallback: ConnectionCallback? = null
        var customDialogCallback: CustomDialogCallback? = null

        fun build(): CustomNoInternetDialog {
            val dialog = CustomNoInternetDialog(
                activity,
                cancelable,

                layoutResourceId,
                customDialogCallback
                    ?: throw RuntimeException("CustomDialogCallback must be implemented!")
            )

            dialog.connectionCallback = connectionCallback

            return dialog
        }

    }
}