package org.imaginativeworld.oopsnointernet.snackbars.fire

import com.google.android.material.snackbar.BaseTransientBottomBar
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback

class SnackbarPropertiesFire(
    var connectionCallback: ConnectionCallback?,
    @BaseTransientBottomBar.Duration var duration: Int,
    var noInternetConnectionMessage: String,
    var onAirplaneModeMessage: String,
    var snackbarActionText: String,
    var showActionToDismiss: Boolean,
    var snackbarDismissActionText: String
)