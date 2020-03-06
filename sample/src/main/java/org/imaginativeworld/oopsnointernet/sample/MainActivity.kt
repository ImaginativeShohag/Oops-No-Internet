package org.imaginativeworld.oopsnointernet.sample


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.imaginativeworld.oopsnointernet.ConnectionCallback
import org.imaginativeworld.oopsnointernet.NoInternetDialog
import org.imaginativeworld.oopsnointernet.NoInternetSnackbar

class MainActivity : AppCompatActivity() {

    // No Internet Dialog
    private var noInternetDialog: NoInternetDialog? = null

    // No Internet Snackbar
    private var noInternetSnackbar: NoInternetSnackbar? = null

    private var selectedRadioId = R.id.radio_dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radio_group.setOnCheckedChangeListener { _, checkedId ->

            selectedRadioId = checkedId

            init()

        }

        btn_go_to_java_activity.setOnClickListener {

            startActivity(Intent(this, JavaActivity::class.java))

        }
    }

    override fun onResume() {
        super.onResume()

        init()
    }

    private fun init() {
        when (selectedRadioId) {

            R.id.radio_dialog -> {

                noInternetSnackbar?.destroy()

                // No Internet Dialog
                noInternetDialog = NoInternetDialog.Builder(this)
                    .apply {
                        connectionCallback = object : ConnectionCallback { // Optional
                            override fun hasActiveConnection(hasActiveConnection: Boolean) {
                                // ...
                            }
                        }
                        cancelable = false // Optional
                        noInternetConnectionTitle = "No Internet" // Optional
                        noInternetConnectionMessage =
                            "Check your Internet connection and try again." // Optional
                        showInternetOnButtons = true // Optional
                        pleaseTurnOnText = "Please turn on" // Optional
                        wifiOnButtonText = "Wifi" // Optional
                        mobileDataOnButtonText = "Mobile data" // Optional

                        onAirplaneModeTitle = "No Internet" // Optional
                        onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                        pleaseTurnOffText = "Please turn off" // Optional
                        airplaneModeOffButtonText = "Airplane mode" // Optional
                        showAirplaneModeOffButtons = true // Optional
                    }
                    .build()

            }

            R.id.radio_snackbar -> {

                noInternetDialog?.destroy()

                // No Internet Snackbar
                noInternetSnackbar =
                    NoInternetSnackbar.Builder(this, findViewById(android.R.id.content))
                        .apply {
                            connectionCallback = object : ConnectionCallback { // Optional
                                override fun hasActiveConnection(hasActiveConnection: Boolean) {
                                    // ...
                                }
                            }
                            indefinite = true // Optional
                            noInternetConnectionMessage =
                                "No active Internet connection!" // Optional
                            onAirplaneModeMessage =
                                "You have turned on the airplane mode!" // Optional
                            snackbarActionText = "Settings" // Optional
                            showActionToDismiss = false // Optional
                            snackbarDismissActionText = "OK" // Optional
                        }
                        .build()

            }

        }
    }

    override fun onPause() {
        super.onPause()

        // No Internet Dialog
        noInternetDialog?.destroy()

        // No Internet Snackbar
        noInternetSnackbar?.destroy()
    }
}
