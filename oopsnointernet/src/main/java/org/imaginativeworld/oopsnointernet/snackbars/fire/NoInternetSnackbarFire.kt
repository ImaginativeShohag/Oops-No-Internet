package org.imaginativeworld.oopsnointernet.snackbars.fire

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.google.android.material.snackbar.Snackbar
import org.imaginativeworld.oopsnointernet.R
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.components.NoInternetObserveComponent
import org.imaginativeworld.oopsnointernet.utils.NoInternetUtils

class NoInternetSnackbarFire(
    private val parent: ViewGroup,
    lifecycle: Lifecycle,
    private val snackbarProperties: SnackbarPropertiesFire,
) : LifecycleObserver {

    private val applicationContext = parent.context.applicationContext

    private var noInternetObserveComponent: NoInternetObserveComponent

    private var connectionCallback: ConnectionCallback? = snackbarProperties.connectionCallback

    private var snackBar: FireSnackbar? = null

    init {
        noInternetObserveComponent = NoInternetObserveComponent(
            applicationContext,
            lifecycle,
            object : NoInternetObserveComponent.NoInternetObserverListener {
                override fun onStart() {
                    /* no-op */
                }

                override fun onConnected() {

                    connectionCallback?.hasActiveConnection(true)

                    dismiss()

                }

                override fun onDisconnected(isAirplaneModeOn: Boolean) {

                    connectionCallback?.hasActiveConnection(false)

                    show()

                }

                override fun onStop() {

                    destroy()

                }

            }
        )
    }

    private fun getSnackbar(): FireSnackbar? {
        if (snackBar == null) {

            val isAirplaneModeOn = NoInternetUtils.isAirplaneModeOn(applicationContext)

            snackBar = FireSnackbar.make(
                parent,
                if (isAirplaneModeOn)
                    snackbarProperties.onAirplaneModeMessage
                else
                    snackbarProperties.noInternetConnectionMessage,
                snackbarProperties.duration
            ).apply {

                // Update action button
                if (snackbarProperties.showActionToDismiss) {
                    setAction(
                        snackbarProperties.snackbarDismissActionText
                    ) {
                        // dismiss
                    }
                } else {
                    setAction(
                        snackbarProperties.snackbarActionText
                    ) {
                        if (isAirplaneModeOn) {
                            NoInternetUtils.turnOffAirplaneMode(context.applicationContext)
                        } else {
                            NoInternetUtils.turnOnMobileData(context.applicationContext)
                        }
                    }
                }

            }

        }

        return snackBar
    }

    fun dismiss() {
        getSnackbar()?.dismiss()

        snackBar = null
    }

    fun show() {
        if (NoInternetUtils.isAirplaneModeOn(applicationContext))
            snackbarProperties.onAirplaneModeMessage
        else
            snackbarProperties.noInternetConnectionMessage

        getSnackbar()?.show()
    }

    fun destroy() {
        getSnackbar()?.dismiss()
    }

    /**
     * Manually start listening.
     */
    fun start() {
        noInternetObserveComponent.start()
    }

    /**
     * Manually stop listening.
     */
    fun stop() {
        noInternetObserveComponent.stop()
    }

    class Builder(
        private val parent: ViewGroup,
        private val lifecycle: Lifecycle
    ) {
        val snackbarProperties = SnackbarPropertiesFire(
            connectionCallback = null,
            duration = Snackbar.LENGTH_INDEFINITE,
            noInternetConnectionMessage = parent.context.getString(R.string.default_snackbar_message),
            onAirplaneModeMessage =
            parent.context.getString(R.string.default_airplane_mode_snackbar_message),
            snackbarActionText = parent.context.getString(R.string.settings),
            showActionToDismiss = false,
            snackbarDismissActionText = parent.context.getString(R.string.ok)
        )

        fun build(): NoInternetSnackbarFire {
            return NoInternetSnackbarFire(
                parent,
                lifecycle,
                snackbarProperties
            )
        }

    }

}