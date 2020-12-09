package org.imaginativeworld.oopsnointernet.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal
import org.imaginativeworld.oopsnointernet.sample.databinding.ActivityKotlinExampleBinding
import org.imaginativeworld.oopsnointernet.snackbars.fire.NoInternetSnackbarFire
import timber.log.Timber

class KotlinExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKotlinExampleBinding

    // No Internet Dialog: Pendulum
    private var noInternetDialogPendulum: NoInternetDialogPendulum? = null

    // No Internet Dialog: Signal
    private var noInternetDialogSignal: NoInternetDialogSignal? = null

    // No Internet Snackbar: Fire
    private var noInternetSnackbarFire: NoInternetSnackbarFire? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        Timber.d("lifecycle.currentState: ${lifecycle.currentState}")

        binding = ActivityKotlinExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra(Constants.KEY_TYPE)

        when (type) {
            Constants.TYPE_DIALOG_PENDULUM -> {
                noInternetDialogPendulum = NoInternetDialogPendulum.Builder(
                    this,
                    lifecycle
                ).apply {
                    dialogProperties.apply {
                        cancelable = true
                    }
                }.build()
            }

            Constants.TYPE_DIALOG_SIGNAL -> {
                noInternetDialogSignal = NoInternetDialogSignal.Builder(
                    this,
                    lifecycle
                ).apply {
                    dialogProperties.apply {
                        cancelable = true
                    }
                }.build()
            }

            Constants.TYPE_SNACKBAR_FIRE -> {
                noInternetSnackbarFire = NoInternetSnackbarFire.Builder(
                    binding.mainContainer,
                    lifecycle
                ).apply {
                    snackbarProperties.apply {
                        showActionToDismiss = true
                    }
                }.build()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
        Timber.d("lifecycle.currentState: ${lifecycle.currentState}")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        Timber.d("lifecycle.currentState: ${lifecycle.currentState}")

        binding.root.postDelayed({
            Timber.d("postDelayed")
            Timber.d("lifecycle.currentState: ${lifecycle.currentState}")
        }, 1000)
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
        Timber.d("lifecycle.currentState: ${lifecycle.currentState}")

        noInternetDialogPendulum?.show()
        noInternetDialogSignal?.show()
        noInternetSnackbarFire?.show()
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
        Timber.d("lifecycle.currentState: ${lifecycle.currentState}")

        noInternetDialogPendulum?.show()
        noInternetDialogSignal?.show()
        noInternetSnackbarFire?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        Timber.d("lifecycle.currentState: ${lifecycle.currentState}")

        noInternetDialogPendulum?.show()
        noInternetDialogSignal?.show()
        noInternetSnackbarFire?.show()
    }
}