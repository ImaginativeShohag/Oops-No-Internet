package org.imaginativeworld.oopsnointernet.dialogs.base

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.components.NoInternetObserveComponent
import org.imaginativeworld.oopsnointernet.utils.LogUtils
import org.imaginativeworld.oopsnointernet.utils.NoInternetUtils

abstract class BaseNoInternetDialog(
    private val activity: Activity,
    private val lifecycle: Lifecycle,
    private val dialogProperties: BaseDialogProperties,
    @StyleRes themeResId: Int
) : Dialog(activity, themeResId), LifecycleObserver {

    constructor(
        activity: Activity,
        lifecycle: Lifecycle,
        dialogProperties: BaseDialogProperties
    ) : this(activity, lifecycle, dialogProperties, 0)

    private val TAG = "BaseNoInternetDialog"

    private var noInternetObserveComponent: NoInternetObserveComponent

    private var connectionCallback: ConnectionCallback? = dialogProperties.connectionCallback

    init {
        noInternetObserveComponent = NoInternetObserveComponent(
            activity.applicationContext,
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d(TAG, "onCreate")

        lifecycle.addObserver(this)

        setLayout()
        initView()

        setProperties()
    }

    private fun setProperties() {
        LogUtils.d(TAG, "setProperties")

        setCancelable(dialogProperties.cancelable)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    override fun show() {
        LogUtils.d(TAG, "show")

        if (!activity.isFinishing && lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            LogUtils.d(TAG, "showing")

            super.show()

            onShow(NoInternetUtils.isAirplaneModeOn(activity))
        }
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
    }

    override fun onStop() {
        super.onStop()

        onDismiss()
    }

    /**
     * Destroy the dialog.
     */
    fun destroy() {
        LogUtils.d(TAG, "destroy")

        onDestroy()

        dismiss()
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

    /**
     * Set content view here.
     */
    internal abstract fun setLayout()

    /**
     * Initialize view elements.
     */
    internal abstract fun initView()

    /**
     * Thing to do after dialog `show()` called, like start the animations etc.
     */
    internal abstract fun onShow(isAirplaneModeOn: Boolean)

    /**
     * Thing to do on dismiss dialog, like stop the animations etc.
     */
    internal abstract fun onDismiss()

    /**
     * Thing to do on destroy dialog.
     */
    internal abstract fun onDestroy()

}