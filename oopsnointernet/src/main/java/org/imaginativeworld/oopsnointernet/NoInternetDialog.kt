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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import org.imaginativeworld.oopsnointernet.databinding.DialogNoInternetBinding

class NoInternetDialog private constructor(
    private val activity: Activity,
    private val cancelable: Boolean,

    private val noInternetConnectionTitle: String,
    private val noInternetConnectionMessage: String,
    private val showInternetOnButtons: Boolean,
    private val pleaseTurnOnText: String,
    private val wifiOnButtonText: String,
    private val mobileDataOnButtonText: String,

    private val onAirplaneModeTitle: String,
    private val onAirplaneModeMessage: String,
    private val pleaseTurnOffText: String,
    private val airplaneModeOffButtonText: String,
    private val showAirplaneModeOffButtons: Boolean
) : Dialog(activity), View.OnClickListener {

    private val TAG = "NoInternetDialog"

    private lateinit var binding: DialogNoInternetBinding

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
        binding = DialogNoInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProperties()
        initMainWindow()
        initViews()
        initListeners()
        initAnimations()
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

            if (widthDp < 352) { // 320dp width + 16dp*2 margin
                binding.mainContainer.layoutParams = FrameLayout.LayoutParams(
                    (widthDp - 32).toPx(context),
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            }
        }

        // Init other views
        binding.tvPleaseTurnOn.text = pleaseTurnOnText
        binding.btnWifiOn.text = wifiOnButtonText
        binding.btnMobileOn.text = mobileDataOnButtonText

        binding.tvPleaseTurnOff.text = pleaseTurnOffText
        binding.btnAirplaneOff.text = airplaneModeOffButtonText
    }

    private fun updateViews() {
        if (NoInternetUtils.isAirplaneModeOn(activity)) {

            binding.tvTitle.text = onAirplaneModeTitle
            binding.tvMessage.text = onAirplaneModeMessage

            hideNoInternetButtonViews()

            if (showAirplaneModeOffButtons) {
                showTurnOffAirplaneModeButtonViews()
            } else {
                hideTurnOffAirplaneModeButtonViews()
            }

        } else {

            binding.tvTitle.text = noInternetConnectionTitle
            binding.tvMessage.text = noInternetConnectionMessage

            hideTurnOffAirplaneModeButtonViews()

            if (showInternetOnButtons) {
                showNoInternetButtonViews()
            } else {
                hideNoInternetButtonViews()
            }

        }

        updateMessageLayoutParams()
    }

    private fun showNoInternetButtonViews() {
        binding.groupTurnOnInternet.visibility = View.VISIBLE
    }

    private fun hideNoInternetButtonViews() {
        binding.groupTurnOnInternet.visibility = View.GONE
    }

    private fun showTurnOffAirplaneModeButtonViews() {
        binding.groupTurnOffAirplane.visibility = View.VISIBLE
    }

    private fun hideTurnOffAirplaneModeButtonViews() {
        binding.groupTurnOffAirplane.visibility = View.GONE
    }

    private fun updateMessageLayoutParams() {
        if (!showInternetOnButtons && !showAirplaneModeOffButtons) {
            val params = binding.tvMessage.layoutParams as ConstraintLayout.LayoutParams
            params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            binding.tvMessage.requestLayout()
        }
    }

    private fun initListeners() {
        binding.btnWifiOn.setOnClickListener(this)
        binding.btnMobileOn.setOnClickListener(this)
        binding.btnAirplaneOff.setOnClickListener(this)
    }

    private fun initAnimations() {
        binding.noInternetImg1.animation = AnimationUtils.loadAnimation(activity, R.anim.wave_1)
        binding.noInternetImg2.animation = AnimationUtils.loadAnimation(activity, R.anim.wave_2)

        if (NoInternetUtils.isAirplaneModeOn(activity)) {
            binding.imgAirplane.visibility = View.VISIBLE

            val airplaneStart = AnimationUtils.loadAnimation(activity, R.anim.airplane_start)
            val airplaneEnd = AnimationUtils.loadAnimation(activity, R.anim.airplane_end)

            airplaneStart.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    binding.imgAirplane.startAnimation(airplaneEnd)
                }
            })

            airplaneEnd.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    binding.imgAirplane.startAnimation(airplaneStart)
                }
            })

            binding.imgAirplane.animation = airplaneStart
        } else {
            binding.imgAirplane.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {

        v?.also {

            when (it.id) {
                R.id.btn_wifi_on -> {

                    NoInternetUtils.turnOnWifi(context)

                }

                R.id.btn_mobile_on -> {

                    NoInternetUtils.turnOnMobileData(context)

                }

                R.id.btn_airplane_off -> {

                    NoInternetUtils.turnOffAirplaneMode(context)

                }
            }

        }

    }

    override fun show() {
        if (!activity.isFinishing) {
            super.show()

            updateViews()
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
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)

        dismiss()
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

        var onAirplaneModeTitle = activity.getString(R.string.default_title)
        var onAirplaneModeMessage = activity.getString(R.string.default_airplane_mode_message)
        var pleaseTurnOffText = activity.getString(R.string.please_turn_off)
        var airplaneModeOffButtonText = activity.getString(R.string.airplane_mode)
        var showAirplaneModeOffButtons = true

        fun build(): NoInternetDialog {
            val dialog = NoInternetDialog(
                activity,
                cancelable,
                noInternetConnectionTitle,
                noInternetConnectionMessage,
                showInternetOnButtons,
                pleaseTurnOnText,
                wifiOnButtonText,
                mobileDataOnButtonText,
                onAirplaneModeTitle,
                onAirplaneModeMessage,
                pleaseTurnOffText,
                airplaneModeOffButtonText,
                showAirplaneModeOffButtons
            )

            dialog.connectionCallback = connectionCallback

            return dialog
        }

    }
}