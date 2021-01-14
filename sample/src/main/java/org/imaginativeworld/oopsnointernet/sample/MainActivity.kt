package org.imaginativeworld.oopsnointernet.sample


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import org.imaginativeworld.oopsnointernet.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var selectedType = Constants.TYPE_DIALOG_PENDULUM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init views
        if (isNightMode()) {
            binding.fabThemeToggle.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_round_bedtime_24
                )
            )
        } else {
            binding.fabThemeToggle.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_round_wb_sunny_24
                )
            )
        }

        // Init listeners
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

            if (checkedId == R.id.radio_dialog_pendulum) {
                selectedType = Constants.TYPE_DIALOG_PENDULUM
            } else if (checkedId == R.id.radio_dialog_signal) {
                selectedType = Constants.TYPE_DIALOG_SIGNAL
            } else if (checkedId == R.id.radio_snackbar_fire) {
                selectedType = Constants.TYPE_SNACKBAR_FIRE
            }

        }

        binding.fabThemeToggle.setOnClickListener {

            if (isNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

        }

        binding.btnKotlin.setOnClickListener {

            startActivity(Intent(this, KotlinExampleActivity::class.java)
                .apply {
                    this.putExtra(Constants.KEY_TYPE, selectedType)
                })

        }

        binding.btnJava.setOnClickListener {

            startActivity(Intent(this, JavaExampleActivity::class.java)
                .apply {
                    this.putExtra(Constants.KEY_TYPE, selectedType)
                })

        }
    }

    private fun isNightMode(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}
