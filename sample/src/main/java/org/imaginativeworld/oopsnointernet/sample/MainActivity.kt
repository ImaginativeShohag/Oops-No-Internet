package org.imaginativeworld.oopsnointernet.sample


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.imaginativeworld.oopsnointernet.CustomNoInternetDialog
import org.imaginativeworld.oopsnointernet.NoInternetDialog
import org.imaginativeworld.oopsnointernet.NoInternetDialogNew
import org.imaginativeworld.oopsnointernet.NoInternetSnackbar
import org.imaginativeworld.oopsnointernet.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // No Internet Dialog
    private var noInternetDialog: NoInternetDialog? = null

    // No Internet Snackbar
    private var noInternetSnackbar: NoInternetSnackbar? = null

    // Custom No Internet Dialog
    private var customNoInternetDialog: CustomNoInternetDialog? = null

    private var selectedRadioId = R.id.radio_dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        NoInternetDialogNew.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                cancelable = true
            }
        }.build()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

            selectedRadioId = checkedId

            init()

        }

        binding.btnGoToJavaActivity.setOnClickListener {

            startActivity(Intent(this, JavaActivity::class.java))

        }
    }

    override fun onResume() {
        super.onResume()

        init()
    }

    private fun init() {

//        noInternetDialog = NoInternetDialog.Builder(this)
//            .apply {
//                connectionCallback = object : ConnectionCallback { // Optional
//                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
//                        // ...
//                    }
//                }
//                cancelable = false // Optional
//                noInternetConnectionTitle = "No Internet" // Optional
//                noInternetConnectionMessage =
//                    "Check your Internet connection and try again." // Optional
//                showInternetOnButtons = true // Optional
//                pleaseTurnOnText = "Please turn on" // Optional
//                wifiOnButtonText = "Wifi" // Optional
//                mobileDataOnButtonText = "Mobile data" // Optional
//
//                onAirplaneModeTitle = "No Internet" // Optional
//                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
//                pleaseTurnOffText = "Please turn off" // Optional
//                airplaneModeOffButtonText = "Airplane mode" // Optional
//                showAirplaneModeOffButtons = true // Optional
//            }
//            .build()

        when (selectedRadioId) {

//            R.id.radio_dialog -> {
//
//                noInternetSnackbar?.destroy()
//                customNoInternetDialog?.destroy()
//
//                // No Internet Dialog
//                noInternetDialog = NoInternetDialog.Builder(this)
//                    .apply {
//                        connectionCallback = object : ConnectionCallback { // Optional
//                            override fun hasActiveConnection(hasActiveConnection: Boolean) {
//                                // ...
//                            }
//                        }
//                        cancelable = false // Optional
//                        noInternetConnectionTitle = "No Internet" // Optional
//                        noInternetConnectionMessage =
//                            "Check your Internet connection and try again." // Optional
//                        showInternetOnButtons = true // Optional
//                        pleaseTurnOnText = "Please turn on" // Optional
//                        wifiOnButtonText = "Wifi" // Optional
//                        mobileDataOnButtonText = "Mobile data" // Optional
//
//                        onAirplaneModeTitle = "No Internet" // Optional
//                        onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
//                        pleaseTurnOffText = "Please turn off" // Optional
//                        airplaneModeOffButtonText = "Airplane mode" // Optional
//                        showAirplaneModeOffButtons = true // Optional
//                    }
//                    .build()
//
//            }
//
//            R.id.radio_snackbar -> {
//
//                noInternetDialog?.destroy()
//                customNoInternetDialog?.dismiss()
//
//                // No Internet Snackbar
//                noInternetSnackbar =
//                    NoInternetSnackbar.Builder(this, findViewById(android.R.id.content))
//                        .apply {
//                            connectionCallback = object : ConnectionCallback { // Optional
//                                override fun hasActiveConnection(hasActiveConnection: Boolean) {
//                                    // ...
//                                }
//                            }
//                            indefinite = true // Optional
//                            noInternetConnectionMessage =
//                                "No active Internet connection!" // Optional
//                            onAirplaneModeMessage =
//                                "You have turned on the airplane mode!" // Optional
//                            snackbarActionText = "Settings" // Optional
//                            showActionToDismiss = false // Optional
//                            snackbarDismissActionText = "OK" // Optional
//                        }
//                        .build()
//
//            }
//
//            R.id.radio_custom_dialog -> {

//            else -> {
//
//                noInternetDialog?.destroy()
//                noInternetSnackbar?.destroy()
//
//                // Custom No Internet Dialog
//                customNoInternetDialog = CustomNoInternetDialog.Builder(
//                    this,
//                    R.layout.custom_dialog_no_internet
//                )
//                    .apply {
//                        connectionCallback = object : ConnectionCallback { // Optional
//                            override fun hasActiveConnection(hasActiveConnection: Boolean) {
//                                // ...
//                            }
//                        }
//                        cancelable = false
//                        customDialogCallback = object : CustomDialogCallback {
//                            override fun initDialogCallback(
//                                dialog: CustomNoInternetDialog,
//                                view: View?,
//                                isAirplaneModeOn: Boolean
//                            ) {
//
//                                view?.also { dialogView ->
//
//                                    val tvBody = dialogView.findViewById<TextView>(R.id.tv_body)
//
//                                    val tvHintWifiData =
//                                        dialogView.findViewById<TextView>(R.id.tv_hint_wifi_data)
//                                    val btnWifi = dialogView.findViewById<Button>(R.id.btn_wifi)
//                                    val btnMobileData =
//                                        dialogView.findViewById<Button>(R.id.btn_mobile_data)
//
//                                    val tvHintAirplane =
//                                        dialogView.findViewById<TextView>(R.id.tv_hint_airplane)
//                                    val btnAirplaneOff =
//                                        dialogView.findViewById<Button>(R.id.btn_airplane_off)
//
//                                    val roundViewOne =
//                                        dialogView.findViewById<View>(R.id.circle_view_one)
//                                    val roundViewTwo =
//                                        dialogView.findViewById<View>(R.id.circle_view_two)
//                                    val roundViewThree =
//                                        dialogView.findViewById<View>(R.id.circle_view_three)
//                                    val imgAirplane =
//                                        dialogView.findViewById<View>(R.id.img_airplane)
//                                    val imgCloudOne =
//                                        dialogView.findViewById<View>(R.id.img_cloud_one)
//                                    val imgCloudTwo =
//                                        dialogView.findViewById<View>(R.id.img_cloud_two)
//                                    val imgCloudThree =
//                                        dialogView.findViewById<View>(R.id.img_cloud_three)
//
//
//                                    // Listeners
//                                    btnWifi.setOnClickListener {
//                                        NoInternetUtils.turnOnWifi(this@MainActivity)
//                                    }
//
//                                    btnMobileData.setOnClickListener {
//                                        NoInternetUtils.turnOnMobileData(this@MainActivity)
//                                    }
//
//                                    btnAirplaneOff.setOnClickListener {
//                                        NoInternetUtils.turnOffAirplaneMode(this@MainActivity)
//                                    }
//
//
//                                    // Airplane mode
//                                    if (isAirplaneModeOn) {
//                                        tvBody.text =
//                                            getString(R.string.default_airplane_mode_message)
//
//                                        imgAirplane.visibility = View.VISIBLE
//                                        tvHintAirplane.visibility = View.VISIBLE
//                                        btnAirplaneOff.visibility = View.VISIBLE
//
//                                        tvHintWifiData.visibility = View.GONE
//                                        btnWifi.visibility = View.GONE
//                                        btnMobileData.visibility = View.GONE
//                                    } else {
//                                        tvBody.text = getString(R.string.default_message)
//
//                                        imgAirplane.visibility = View.GONE
//                                        tvHintAirplane.visibility = View.GONE
//                                        btnAirplaneOff.visibility = View.GONE
//
//                                        tvHintWifiData.visibility = View.VISIBLE
//                                        btnWifi.visibility = View.VISIBLE
//                                        btnMobileData.visibility = View.VISIBLE
//                                    }
//
//
//                                    // ----------------------------------------------------------------
//                                    // Animations
//                                    // ----------------------------------------------------------------
//                                    // Note: We need to post the animations, so that following things
//                                    // will be run after view loaded. Without loading the view we
//                                    // cannot get the width of the views, which is necessary for
//                                    // our animations.
//                                    // ----------------------------------------------------------------
//                                    dialogView.post {
//                                        val dialogWidth = dialogView.width
//
//                                        // ----------------------------------------------------------------
//                                        // Signal
//                                        // ----------------------------------------------------------------
//                                        ValueAnimator.ofFloat(0f, 0f, 1f).apply {
//                                            duration = 5000
//                                            repeatCount = ValueAnimator.INFINITE
//                                            startDelay = 0
//                                            addUpdateListener {
//
//                                                roundViewOne.scaleX = it.animatedValue as Float
//                                                roundViewOne.scaleY = it.animatedValue as Float
//
//                                                roundViewOne.alpha = 1 - it.animatedValue as Float
//
//                                            }
//                                            start()
//                                        }
//                                        ValueAnimator.ofFloat(0f, 0f, 1f).apply {
//                                            duration = 5000
//                                            repeatCount = ValueAnimator.INFINITE
//                                            startDelay = 600
//                                            addUpdateListener {
//
//                                                roundViewTwo.scaleX = it.animatedValue as Float
//                                                roundViewTwo.scaleY = it.animatedValue as Float
//
//                                                roundViewTwo.alpha = 1 - it.animatedValue as Float
//
//                                            }
//                                            start()
//                                        }
//                                        ValueAnimator.ofFloat(0f, 0f, 1f).apply {
//                                            duration = 5000
//                                            repeatCount = ValueAnimator.INFINITE
//                                            startDelay = 1200
//                                            addUpdateListener {
//
//                                                roundViewThree.scaleX = it.animatedValue as Float
//                                                roundViewThree.scaleY = it.animatedValue as Float
//
//                                                roundViewThree.alpha = 1 - it.animatedValue as Float
//
//                                            }
//                                            start()
//                                        }
//
//
//                                        // ----------------------------------------------------------------
//                                        // Airplane
//                                        // ----------------------------------------------------------------
//                                        if (isAirplaneModeOn) {
//                                            ValueAnimator.ofFloat(1f, .65f, 1f).apply {
//                                                duration = 5000
//                                                repeatCount = ValueAnimator.INFINITE
//                                                startDelay = 0
//                                                addUpdateListener {
//
//                                                    imgAirplane.scaleX = it.animatedValue as Float
//                                                    imgAirplane.scaleY = it.animatedValue as Float
//
//                                                }
//                                                start()
//                                            }
//                                            ValueAnimator.ofFloat(1f, .4f, 1f).apply {
//                                                duration = 5000
//                                                repeatCount = ValueAnimator.INFINITE
//                                                startDelay = 0
//                                                addUpdateListener {
//
//                                                    imgAirplane.alpha = it.animatedValue as Float
//
//                                                }
//                                                start()
//                                            }
//
//                                            ValueAnimator.ofFloat(
//                                                -(dialogWidth * .6).toFloat(),
//                                                (dialogWidth * .6).toFloat()
//                                            )
//                                                .apply {
//                                                    duration = 7000
//                                                    repeatCount = ValueAnimator.INFINITE
//                                                    startDelay = 0
//                                                    addUpdateListener {
//
//                                                        imgAirplane.translationX =
//                                                            it.animatedValue as Float
//
//                                                    }
//                                                    start()
//                                                }
//                                        }
//
//                                        // ----------------------------------------------------------------
//                                        // Clouds
//                                        // ----------------------------------------------------------------
//                                        ValueAnimator.ofFloat(
//                                            (dialogWidth * .6).toFloat(),
//                                            -(dialogWidth * .6).toFloat()
//                                        )
//                                            .apply {
//                                                duration = 8000
//                                                repeatCount = ValueAnimator.INFINITE
//                                                startDelay = 0
//                                                interpolator = LinearInterpolator()
//                                                addUpdateListener {
//
//                                                    imgCloudOne.translationX =
//                                                        it.animatedValue as Float
//
//                                                }
//                                                start()
//                                            }
//                                        ValueAnimator.ofFloat(
//                                            (dialogWidth * .6).toFloat(),
//                                            -(dialogWidth * .6).toFloat()
//                                        )
//                                            .apply {
//                                                duration = 9000
//                                                repeatCount = ValueAnimator.INFINITE
//                                                startDelay = 250
//                                                interpolator = LinearInterpolator()
//                                                addUpdateListener {
//
//                                                    imgCloudTwo.translationX =
//                                                        it.animatedValue as Float
//
//                                                }
//                                                start()
//                                            }
//                                        ValueAnimator.ofFloat(
//                                            (dialogWidth * .6).toFloat(),
//                                            -(dialogWidth * .6).toFloat()
//                                        )
//                                            .apply {
//                                                duration = 10000
//                                                repeatCount = ValueAnimator.INFINITE
//                                                startDelay = 750
//                                                interpolator = LinearInterpolator()
//                                                addUpdateListener {
//
//                                                    imgCloudThree.translationX =
//                                                        it.animatedValue as Float
//
//                                                }
//                                                start()
//                                            }
//                                    }
//
//
//                                }
//
//                            }
//
//                            override fun onShowCallback(
//                                dialog: CustomNoInternetDialog
//                            ) {
//
//
//                            }
//
//                            override fun onDestroyCallback(
//                                dialog: CustomNoInternetDialog
//                            ) {
//
//                            }
//
//                        }
//                    }
//                    .build()
//
//            }

        }
    }

    override fun onPause() {
        super.onPause()

        // No Internet Dialog
        noInternetDialog?.destroy()

        // No Internet Snackbar
        noInternetSnackbar?.destroy()

        // Custom No Internet Dialog
        customNoInternetDialog?.destroy()
    }
}
