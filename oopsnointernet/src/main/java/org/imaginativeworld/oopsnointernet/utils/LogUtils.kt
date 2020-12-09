package org.imaginativeworld.oopsnointernet.utils

import android.util.Log
import org.imaginativeworld.oopsnointernet.BuildConfig

internal object LogUtils {
    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg)
        }
    }
}