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
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.dialog_no_internet.*

class NoInternetDialog private constructor(
    private val activity: Activity,
    private val cancellable: Boolean,
    private val noInternetConnectionTitle: String,
    private val noInternetConnectionMessage: String,
    private val showInternetOnButtons: Boolean,
    private val pleaseTurnOnText: String,
    private val wifiOnButtonText: String,
    private val mobileDataOnButtonText: String
) : Dialog(activity), View.OnClickListener {

    private val TAG = "NoInternetDialog"

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

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
                override fun onAvailable(network: Network?) {
                    Log.d(TAG, "onAvailable(): ${network.toString()}")

                    dismiss()

                    connectionCallback?.hasActiveConnection(true)
                }

                override fun onLost(network: Network?) {
                    Log.d(TAG, "onLost(): ${network.toString()}")

                    showDialog()

                    connectionCallback?.hasActiveConnection(false)
                }
            }

            return connectivityManagerCallback
        } else {
            throw IllegalAccessError("Should not happened")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_no_internet)

        initProperties()
        initMainWindow()
        initViews()
        initListeners()
        initAnimations()
    }

    private fun initProperties() {
        setCancelable(cancellable)
    }

    private fun initMainWindow() {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initViews() {
        tv_title.text = noInternetConnectionTitle
        tv_message.text = noInternetConnectionMessage

        tv_please_turn_on.text = pleaseTurnOnText
        btn_wifi_on.text = wifiOnButtonText
        btn_mobile_on.text = mobileDataOnButtonText

        if (!showInternetOnButtons) {
            tv_please_turn_on.visibility = View.GONE
            btn_wifi_on.visibility = View.GONE
            btn_mobile_on.visibility = View.GONE
        }
    }

    private fun initListeners() {
        btn_wifi_on.setOnClickListener(this)
        btn_mobile_on.setOnClickListener(this)
    }

    private fun initAnimations() {
        no_internet_img_1.animation = AnimationUtils.loadAnimation(activity, R.anim.wave_1)
        no_internet_img_2.animation = AnimationUtils.loadAnimation(activity, R.anim.wave_2)
    }

    // ----------------------------------------------------------------
    // Listeners
    // ----------------------------------------------------------------

    override fun onClick(v: View?) {

        v?.also {

            when (it.id) {
                R.id.btn_wifi_on -> {

                    NoInternetUtils.turnOnWifi(context)

                }

                R.id.btn_mobile_on -> {

                    NoInternetUtils.turnOnMobileData(context)

                }
            }

        }

    }

    override fun show() {
        if (!activity.isFinishing) {
            super.show()
            initAnimations()
        }
    }

    fun showDialog() {

        Ping().apply {
            connectionCallback = object : ConnectionCallback {
                override fun hasActiveConnection(hasActiveConnection: Boolean) {
                    if (!hasActiveConnection) {
                        show()
                    }
                }
            }

            execute(context)
        }

    }

    fun destroy() {

        dismiss()

        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    class Builder(
        private val activity: Activity
    ) {

        var cancelable = false
        var connectionCallback: ConnectionCallback? = null

        var noInternetConnectionTitle = activity.getString(R.string.default_title)
        var noInternetConnectionMessage = activity.getString(R.string.default_message)
        var showInternetOnButtons = true
        var pleaseTurnOnText = activity.getString(R.string.please_turn_on)
        var wifiOnButtonText = activity.getString(R.string.wifi)
        var mobileDataOnButtonText = activity.getString(R.string.mobile_data)

        fun build(): NoInternetDialog {
            val dialog = NoInternetDialog(
                activity,
                cancelable,
                noInternetConnectionTitle,
                noInternetConnectionMessage,
                showInternetOnButtons,
                pleaseTurnOnText,
                wifiOnButtonText,
                mobileDataOnButtonText
            )

            dialog.connectionCallback = connectionCallback

            return dialog
        }


    }
}