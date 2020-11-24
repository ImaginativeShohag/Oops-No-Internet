package org.imaginativeworld.oopsnointernet

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import org.imaginativeworld.oopsnointernet.databinding.DialogNoInternetBinding

class NoInternetDialogNew private constructor(
    private val activity: Activity,
    lifecycle: Lifecycle,
    private val dialogProperties: DialogProperties
) : BaseNoInternetDialog(activity, lifecycle, dialogProperties), View.OnClickListener {

    private val TAG = "NoInternetDialog"

    private lateinit var binding: DialogNoInternetBinding

    override fun setLayout() {
        binding = DialogNoInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initView() {
        window?.fixWidth(352) // 320dp width + 16dp*2 margin

        initListeners()
        initAnimations()
    }

    private fun initListeners() {
        binding.btnWifiOn.setOnClickListener(this)
        binding.btnMobileOn.setOnClickListener(this)
        binding.btnAirplaneOff.setOnClickListener(this)
    }

    private fun initAnimations() {
        binding.noInternetImg1.animation = AnimationUtils.loadAnimation(activity, R.anim.wave_1)
        binding.noInternetImg2.animation = AnimationUtils.loadAnimation(activity, R.anim.wave_2)

        if (NoInternetUtils.isAirplaneModeOn(activity)) {
            binding.imgAirplane.visibility = View.VISIBLE

            val airplaneStart = AnimationUtils.loadAnimation(activity, R.anim.airplane_start)
            val airplaneEnd = AnimationUtils.loadAnimation(activity, R.anim.airplane_end)

            airplaneStart.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    Log.e(TAG, "onAnimationEnd")

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
        } else {
            binding.imgAirplane.visibility = View.GONE
        }
    }

    private fun stopAnimations() {
        binding.noInternetImg1.animation = null
        binding.noInternetImg2.animation = null

        binding.imgAirplane.clearAnimation()
    }

    override fun onShow(isAirplaneModeOn: Boolean) {
        updateViews(isAirplaneModeOn)
        initAnimations()
    }

    private fun updateViews(isAirplaneModeOn: Boolean) {
        if (isAirplaneModeOn) {

            binding.tvTitle.text = dialogProperties.onAirplaneModeTitle
            binding.tvMessage.text = dialogProperties.onAirplaneModeMessage

            hideNoInternetButtonViews()

            if (dialogProperties.showAirplaneModeOffButtons) {
                showTurnOffAirplaneModeButtonViews()
            } else {
                hideTurnOffAirplaneModeButtonViews()
            }

        } else {

            binding.tvTitle.text = dialogProperties.noInternetConnectionTitle
            binding.tvMessage.text = dialogProperties.noInternetConnectionMessage

            hideTurnOffAirplaneModeButtonViews()

            if (dialogProperties.showInternetOnButtons) {
                showNoInternetButtonViews()
            } else {
                hideNoInternetButtonViews()
            }

        }

        updateMessageLayoutParams()
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
//        stopAnimations()
    }

    override fun onClick(v: View?) {
        v?.also {

            when (it.id) {
                R.id.btn_wifi_on -> {

                    NoInternetUtils.turnOnWifi(context)

                }

                R.id.btn_mobile_on -> {

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
        val dialogProperties = DialogProperties()

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

        fun build(): NoInternetDialogNew {
            return NoInternetDialogNew(
                activity,
                lifecycle,
                dialogProperties
            )
        }

    }
}