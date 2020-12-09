package org.imaginativeworld.oopsnointernet.dialogs.pendulum

import android.app.Activity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import org.imaginativeworld.oopsnointernet.R
import org.imaginativeworld.oopsnointernet.databinding.DialogNoInternetPendulumBinding
import org.imaginativeworld.oopsnointernet.dialogs.base.BaseNoInternetDialog
import org.imaginativeworld.oopsnointernet.utils.NoInternetUtils
import org.imaginativeworld.oopsnointernet.utils.fixWidth

class NoInternetDialogPendulum private constructor(
    private val activity: Activity,
    lifecycle: Lifecycle,
    private val dialogProperties: DialogPropertiesPendulum
) : BaseNoInternetDialog(activity, lifecycle, dialogProperties, R.style.Dialog_Pendulum),
    View.OnClickListener {

    private lateinit var binding: DialogNoInternetPendulumBinding

    override fun setLayout() {
        binding = DialogNoInternetPendulumBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initView() {
        // 320dp width, 16dp*2 margin
        window?.fixWidth(320, 32)

        initListeners()
    }

    private fun initListeners() {
        binding.btnWifiOn.setOnClickListener(this)
        binding.btnMobileDataOn.setOnClickListener(this)
        binding.btnAirplaneOff.setOnClickListener(this)
    }

    override fun onShow(isAirplaneModeOn: Boolean) {
        updateViews(isAirplaneModeOn)
        initAnimations()
    }

    override fun onDismiss() {
        /* no-op */
    }

    private fun updateViews(isAirplaneModeOn: Boolean) {
        if (isAirplaneModeOn) {

            binding.tvTitle.text = dialogProperties.onAirplaneModeTitle
            binding.tvMessage.text = dialogProperties.onAirplaneModeMessage

            binding.imgAirplane.visibility = View.VISIBLE

            hideNoInternetButtonViews()

            if (dialogProperties.showAirplaneModeOffButtons) {
                showTurnOffAirplaneModeButtonViews()
            } else {
                hideTurnOffAirplaneModeButtonViews()
            }

        } else {

            binding.tvTitle.text = dialogProperties.noInternetConnectionTitle
            binding.tvMessage.text = dialogProperties.noInternetConnectionMessage

            binding.imgAirplane.visibility = View.GONE

            hideTurnOffAirplaneModeButtonViews()

            if (dialogProperties.showInternetOnButtons) {
                showNoInternetButtonViews()
            } else {
                hideNoInternetButtonViews()
            }

        }

        updateMessageLayoutParams()
    }

    private fun initAnimations() {
        binding.noInternetImg1.animation = AnimationUtils.loadAnimation(activity, R.anim.wave_1)
        binding.noInternetImg2.animation = AnimationUtils.loadAnimation(activity, R.anim.wave_2)

        if (NoInternetUtils.isAirplaneModeOn(activity)) {

            val airplaneStart = AnimationUtils.loadAnimation(activity, R.anim.airplane_start)
            val airplaneEnd = AnimationUtils.loadAnimation(activity, R.anim.airplane_end)

            airplaneStart.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    binding.imgAirplane.startAnimation(airplaneEnd)
                }
            })

            airplaneEnd.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    binding.imgAirplane.startAnimation(airplaneStart)
                }
            })

            binding.imgAirplane.animation = airplaneStart

        }
    }

    private fun showNoInternetButtonViews() {
        binding.groupTurnOnInternet.visibility = View.VISIBLE
    }

    private fun hideNoInternetButtonViews() {
        binding.groupTurnOnInternet.visibility = View.GONE
    }

    private fun showTurnOffAirplaneModeButtonViews() {
        binding.groupTurnOffAirplane.visibility = View.VISIBLE
    }

    private fun hideTurnOffAirplaneModeButtonViews() {
        binding.groupTurnOffAirplane.visibility = View.GONE
    }

    private fun updateMessageLayoutParams() {
        if (!dialogProperties.showInternetOnButtons && !dialogProperties.showAirplaneModeOffButtons) {
            val params = binding.tvMessage.layoutParams as ConstraintLayout.LayoutParams
            params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            binding.tvMessage.requestLayout()
        }
    }

    override fun onDestroy() {
        /* no-op */
    }

    override fun onClick(v: View?) {
        v?.also {

            when (it.id) {
                R.id.btn_wifi_on -> {

                    NoInternetUtils.turnOnWifi(context)

                }

                R.id.btn_mobile_data_on -> {

                    NoInternetUtils.turnOnMobileData(context)

                }

                R.id.btn_airplane_off -> {

                    NoInternetUtils.turnOffAirplaneMode(context)

                }
            }

        }
    }

    class Builder(
        private val activity: Activity,
        private val lifecycle: Lifecycle
    ) {
        val dialogProperties = DialogPropertiesPendulum()

        init {
            dialogProperties.apply {
                cancelable = false
                connectionCallback = null
                noInternetConnectionTitle = activity.getString(R.string.default_title)
                noInternetConnectionMessage = activity.getString(R.string.default_message)
                showInternetOnButtons = true
                pleaseTurnOnText = activity.getString(R.string.please_turn_on)
                wifiOnButtonText = activity.getString(R.string.wifi)
                mobileDataOnButtonText = activity.getString(R.string.mobile_data)
                onAirplaneModeTitle = activity.getString(R.string.default_title)
                onAirplaneModeMessage = activity.getString(R.string.default_airplane_mode_message)
                pleaseTurnOffText = activity.getString(R.string.please_turn_off)
                airplaneModeOffButtonText = activity.getString(R.string.airplane_mode)
                showAirplaneModeOffButtons = true
            }
        }

        fun build(): NoInternetDialogPendulum {
            return NoInternetDialogPendulum(
                activity,
                lifecycle,
                dialogProperties
            )
        }

    }
}