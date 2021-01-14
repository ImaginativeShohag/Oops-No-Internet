package org.imaginativeworld.oopsnointernet.dialogs.base

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback

abstract class BaseDialogProperties(
    var cancelable: Boolean = false,
    var connectionCallback: ConnectionCallback? = null
)