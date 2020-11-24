package org.imaginativeworld.oopsnointernet

import android.view.View

interface CustomDialogCallback {
    fun initDialogCallback(dialog: CustomNoInternetDialog, view: View?, isAirplaneModeOn: Boolean)
    fun onShowCallback(dialog: CustomNoInternetDialog)
    fun onDestroyCallback(dialog: CustomNoInternetDialog)
}