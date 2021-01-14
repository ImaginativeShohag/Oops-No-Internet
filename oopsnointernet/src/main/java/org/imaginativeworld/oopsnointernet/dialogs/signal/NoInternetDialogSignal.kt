package org.imaginativeworld.oopsnointernet.dialogs.signal

import android.animation.ValueAnimator
import android.app.Activity
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import org.imaginativeworld.oopsnointernet.R
import org.imaginativeworld.oopsnointernet.databinding.DialogNoInternetSignalBinding
import org.imaginativeworld.oopsnointernet.dialogs.base.BaseNoInternetDialog
import org.imaginativeworld.oopsnointernet.utils.NoInternetUtils
import org.imaginativeworld.oopsnointernet.utils.fixWidth
import kotlin.random.Random

class NoInternetDialogSignal private constructor(
    private val activity: Activity,
    lifecycle: Lifecycle,
    private val dialogProperties: DialogPropertiesSignal
) : BaseNoInternetDialog(activity, lifecycle, dialogProperties, R.style.Dialog_Signal),
    View.OnClickListener {

    private lateinit var binding: DialogNoInternetSignalBinding

    private val valueAnimators = mutableListOf<ValueAnimator>()

    override fun setLayout() {
        binding = DialogNoInternetSignalBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initView() {
        // 320dp width, 16dp*2 margin
        window?.fixWidth(320, 32)

        // Init texts
        binding.tvPleaseTurnOn.text = dialogProperties.pleaseTurnOnText
        binding.btnWifiOn.text = dialogProperties.wifiOnButtonText
        binding.btnMobileDataOn.text = dialogProperties.mobileDataOnButtonText

        binding.tvPleaseTurnOff.text = dialogProperties.pleaseTurnOffText
        binding.btnAirplaneOff.text = dialogProperties.airplaneModeOffButtonText

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
        for (valueAnimator in valueAnimators) {
            valueAnimator.repeatCount = 0
            valueAnimator.removeAllListeners()
            valueAnimator.removeAllUpdateListeners()
            valueAnimator.end()
            valueAnimator.cancel()
        }
        valueAnimators.clear()
    }

    /**
     * Init/reset views for animations.
     */
    private fun initViewsForAnimation() {
        // Signals
        binding.circleViewOne.alpha = 0f
        binding.circleViewTwo.alpha = 0f
        binding.circleViewThree.alpha = 0f

        // Clouds
        binding.imgCloudOne.translationX = -1000F
        binding.imgCloudTwo.translationX = -1000F
        binding.imgCloudThree.translationX = -1000F
    }

    private fun updateViews(isAirplaneModeOn: Boolean) {
        initViewsForAnimation()

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
        // ----------------------------------------------------------------
        // Animations
        // ----------------------------------------------------------------
        // Note: We need to post the animations, so that following things
        // will be run after view loaded. Without loading the view we
        // cannot get the width of the views, which is necessary for
        // our animations.
        // ----------------------------------------------------------------
        binding.root.post {
            val dialogWidth = binding.root.width

            // ----------------------------------------------------------------
            // Signals
            // ----------------------------------------------------------------
            valueAnimators.add(ValueAnimator.ofFloat(0f, 0f, 1f).apply {
                duration = 5000
                repeatCount = ValueAnimator.INFINITE
                startDelay = 0
                addUpdateListener {

                    binding.circleViewOne.scaleX = it.animatedValue as Float
                    binding.circleViewOne.scaleY = it.animatedValue as Float

                    binding.circleViewOne.alpha = 1 - it.animatedValue as Float

                }
                start()
            })

            valueAnimators.add(ValueAnimator.ofFloat(0f, 0f, 1f).apply {
                duration = 5000
                repeatCount = ValueAnimator.INFINITE
                startDelay = 600
                addUpdateListener {

                    binding.circleViewTwo.scaleX = it.animatedValue as Float
                    binding.circleViewTwo.scaleY = it.animatedValue as Float

                    binding.circleViewTwo.alpha = 1 - it.animatedValue as Float

                }
                start()
            })

            valueAnimators.add(ValueAnimator.ofFloat(0f, 0f, 1f).apply {
                duration = 5000
                repeatCount = ValueAnimator.INFINITE
                startDelay = 1200
                addUpdateListener {

                    binding.circleViewThree.scaleX = it.animatedValue as Float
                    binding.circleViewThree.scaleY = it.animatedValue as Float

                    binding.circleViewThree.alpha = 1 - it.animatedValue as Float

                }
                start()
            })


            // ----------------------------------------------------------------
            // Airplane
            // ----------------------------------------------------------------
            if (NoInternetUtils.isAirplaneModeOn(activity)) {
                valueAnimators.add(ValueAnimator.ofFloat(1f, .65f, 1f).apply {
                    duration = 5000
                    repeatCount = ValueAnimator.INFINITE
                    startDelay = 0
                    addUpdateListener {

                        binding.imgAirplane.scaleX = it.animatedValue as Float
                        binding.imgAirplane.scaleY = it.animatedValue as Float

                    }
                    start()
                })

                valueAnimators.add(ValueAnimator.ofFloat(1f, .4f, 1f).apply {
                    duration = 5000
                    repeatCount = ValueAnimator.INFINITE
                    startDelay = 0
                    addUpdateListener {

                        binding.imgAirplane.alpha = it.animatedValue as Float

                    }
                    start()
                })

                valueAnimators.add(ValueAnimator.ofFloat(
                    -(dialogWidth * .6).toFloat(),
                    (dialogWidth * .6).toFloat()
                )
                    .apply {
                        duration = 7000
                        repeatCount = ValueAnimator.INFINITE
                        startDelay = 0
                        addUpdateListener {

                            binding.imgAirplane.translationX = it.animatedValue as Float

                        }
                        start()
                    })
            }

            // ----------------------------------------------------------------
            // Clouds
            // ----------------------------------------------------------------
            valueAnimators.add(ValueAnimator.ofFloat(
                (dialogWidth * .6).toFloat(),
                -(dialogWidth * .6).toFloat()
            )
                .apply {
                    duration = Random.nextLong(8000, 16000)
                    repeatCount = ValueAnimator.INFINITE
                    startDelay = Random.nextLong(0, 5000)
                    interpolator = LinearInterpolator()
                    addUpdateListener {

                        binding.imgCloudOne.translationX = it.animatedValue as Float

                    }
                    start()
                })

            valueAnimators.add(ValueAnimator.ofFloat(
                (dialogWidth * .6).toFloat(),
                -(dialogWidth * .6).toFloat()
            )
                .apply {
                    duration = Random.nextLong(8000, 16000)
                    repeatCount = ValueAnimator.INFINITE
                    startDelay = Random.nextLong(0, 5000)
                    interpolator = LinearInterpolator()
                    addUpdateListener {

                        binding.imgCloudTwo.translationX = it.animatedValue as Float

                    }
                    start()
                })

            valueAnimators.add(ValueAnimator.ofFloat(
                (dialogWidth * .6).toFloat(),
                -(dialogWidth * .6).toFloat()
            )
                .apply {
                    duration = Random.nextLong(8000, 16000)
                    repeatCount = ValueAnimator.INFINITE
                    startDelay = Random.nextLong(0, 5000)
                    interpolator = LinearInterpolator()
                    addUpdateListener {

                        binding.imgCloudThree.translationX = it.animatedValue as Float

                    }
                    start()
                })
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
        val dialogProperties = DialogPropertiesSignal()

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

        fun build(): NoInternetDialogSignal {
            return NoInternetDialogSignal(
                activity,
                lifecycle,
                dialogProperties
            )
        }

    }
}