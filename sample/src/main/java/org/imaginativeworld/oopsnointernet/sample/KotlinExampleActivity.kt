package org.imaginativeworld.oopsnointernet.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal
import org.imaginativeworld.oopsnointernet.sample.databinding.ActivityKotlinExampleBinding
import org.imaginativeworld.oopsnointernet.snackbars.fire.NoInternetSnackbarFire

class KotlinExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKotlinExampleBinding

    // No Internet Dialog: Pendulum
    private var noInternetDialogPendulum: NoInternetDialogPendulum? = null

    // No Internet Dialog: Signal
    private var noInternetDialogSignal: NoInternetDialogSignal? = null

    // No Internet Snackbar: Fire
    private var noInternetSnackbarFire: NoInternetSnackbarFire? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKotlinExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra(Constants.KEY_TYPE)

        when (type) {
            Constants.TYPE_DIALOG_PENDULUM -> {

                noInternetDialogPendulum = NoInternetDialogPendulum.Builder(
                    this,
                    lifecycle
                ).apply {
                    dialogProperties.apply {
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
                }.build()

            }

            Constants.TYPE_DIALOG_SIGNAL -> {

                noInternetDialogSignal = NoInternetDialogSignal.Builder(
                    this,
                    lifecycle
                ).apply {
                    dialogProperties.apply {
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
                }.build()

            }

            Constants.TYPE_SNACKBAR_FIRE -> {

                noInternetSnackbarFire = NoInternetSnackbarFire.Builder(
                    binding.mainContainer,
                    lifecycle
                ).apply {
                    snackbarProperties.apply {
                        connectionCallback = object : ConnectionCallback { // Optional
                            override fun hasActiveConnection(hasActiveConnection: Boolean) {
                                // ...
                            }
                        }

                        duration = Snackbar.LENGTH_INDEFINITE // Optional
                        noInternetConnectionMessage = "No active Internet connection!" // Optional
                        onAirplaneModeMessage = "You have turned on the airplane mode!" // Optional
                        snackbarActionText = "Settings" // Optional
                        showActionToDismiss = false // Optional
                        snackbarDismissActionText = "OK" // Optional
                    }
                }.build()

            }
        }

        binding.fabGoBack.setOnClickListener { finish() }
    }
}