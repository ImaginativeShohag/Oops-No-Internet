package org.imaginativeworld.oopsnointernet

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

abstract class NoInternetObserveComponent(
    lifecycle: Lifecycle,
    listener: NoInternetListener
) : LifecycleObserver {












    interface NoInternetListener {
        fun onConnected(networkId: Int)
        fun onDisconnected(isAirplaneModeOn: Boolean)
    }
}