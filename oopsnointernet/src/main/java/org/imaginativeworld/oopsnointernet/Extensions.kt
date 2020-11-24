@file:JvmName("Utils")

package org.imaginativeworld.oopsnointernet

import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowInsets
import android.widget.FrameLayout


/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun Float.toDp(context: Context): Float {
    return this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun Float.toPx(context: Context): Int {
    return (this * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

/**
 * Get app window width.
 *
 * @return screen width in pixels.
 */
fun Window.getAppWindowWidth(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = this.windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

/**
 * Fix width if the screen is small then the layout width.
 *
 * @param layoutSizeInDp Layout width (with margin) in dp.
 */
fun Window.fixWidth(layoutSizeInDp: Int) {
    val widthDp = getAppWindowWidth().toFloat().toDp(context)

    Log.d("Utils.fixWidth", "layoutSizeInDp in Dp: $layoutSizeInDp")
    Log.d("Utils.fixWidth", "width in Dp: $widthDp")

    // Check if the dialog width is bigger then the screen width!
    if (widthDp < layoutSizeInDp) {
        setLayout(
            (widthDp - 32).toPx(context),
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
    }
}