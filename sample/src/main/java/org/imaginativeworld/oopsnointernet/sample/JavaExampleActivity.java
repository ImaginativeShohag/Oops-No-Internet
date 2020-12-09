package org.imaginativeworld.oopsnointernet.sample;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.DialogPropertiesPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum;
import org.imaginativeworld.oopsnointernet.dialogs.signal.DialogPropertiesSignal;
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;
import org.imaginativeworld.oopsnointernet.sample.databinding.ActivityJavaExampleBinding;
import org.imaginativeworld.oopsnointernet.snackbars.fire.NoInternetSnackbarFire;
import org.imaginativeworld.oopsnointernet.snackbars.fire.SnackbarPropertiesFire;

public class JavaExampleActivity extends AppCompatActivity {

    private ActivityJavaExampleBinding binding;

    // No Internet Dialog: Pendulum
    private NoInternetDialogPendulum noInternetDialogPendulum;

    // No Internet Dialog: Signal
    private NoInternetDialogSignal noInternetDialogSignal;

    // No Internet Snackbar: Fire
    private NoInternetSnackbarFire noInternetSnackbarFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaExampleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type = getIntent().getStringExtra(Constants.KEY_TYPE);

        switch (type) {
            case Constants.TYPE_DIALOG_PENDULUM: {

                NoInternetDialogPendulum.Builder builder = new NoInternetDialogPendulum.Builder(
                        this,
                        getLifecycle()
                );

                DialogPropertiesPendulum properties = builder.getDialogProperties();

                properties.setConnectionCallback(new ConnectionCallback() { // Optional
                    @Override
                    public void hasActiveConnection(boolean hasActiveConnection) {
                        // ...
                    }
                });

                properties.setCancelable(false); // Optional
                properties.setNoInternetConnectionTitle("No Internet"); // Optional
                properties.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
                properties.setShowInternetOnButtons(true); // Optional
                properties.setPleaseTurnOnText("Please turn on"); // Optional
                properties.setWifiOnButtonText("Wifi"); // Optional
                properties.setMobileDataOnButtonText("Mobile data"); // Optional

                properties.setOnAirplaneModeTitle("No Internet"); // Optional
                properties.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
                properties.setPleaseTurnOffText("Please turn off"); // Optional
                properties.setAirplaneModeOffButtonText("Airplane mode"); // Optional
                properties.setShowAirplaneModeOffButtons(true); // Optional

                noInternetDialogPendulum = builder.build();

            }
            break;

            case Constants.TYPE_DIALOG_SIGNAL: {

                NoInternetDialogSignal.Builder builder = new NoInternetDialogSignal.Builder(
                        this,
                        getLifecycle()
                );

                DialogPropertiesSignal properties = builder.getDialogProperties();

                properties.setConnectionCallback(new ConnectionCallback() { // Optional
                    @Override
                    public void hasActiveConnection(boolean hasActiveConnection) {
                        // ...
                    }
                });

                properties.setCancelable(false); // Optional
                properties.setNoInternetConnectionTitle("No Internet"); // Optional
                properties.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
                properties.setShowInternetOnButtons(true); // Optional
                properties.setPleaseTurnOnText("Please turn on"); // Optional
                properties.setWifiOnButtonText("Wifi"); // Optional
                properties.setMobileDataOnButtonText("Mobile data"); // Optional

                properties.setOnAirplaneModeTitle("No Internet"); // Optional
                properties.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
                properties.setPleaseTurnOffText("Please turn off"); // Optional
                properties.setAirplaneModeOffButtonText("Airplane mode"); // Optional
                properties.setShowAirplaneModeOffButtons(true); // Optional

                noInternetDialogSignal = builder.build();

            }
            break;

            case Constants.TYPE_SNACKBAR_FIRE: {

                NoInternetSnackbarFire.Builder builder = new NoInternetSnackbarFire.Builder(
                        binding.mainContainer,
                        getLifecycle()
                );

                SnackbarPropertiesFire properties = builder.getSnackbarProperties();

                properties.setConnectionCallback(new ConnectionCallback() { // Optional
                    @Override
                    public void hasActiveConnection(boolean hasActiveConnection) {
                        // ...
                    }
                });

                properties.setDuration(Snackbar.LENGTH_INDEFINITE); // Optional
                properties.setNoInternetConnectionMessage("No active Internet connection!"); // Optional
                properties.setOnAirplaneModeMessage("You have turned on the airplane mode!"); // Optional
                properties.setSnackbarActionText("Settings"); // Optional
                properties.setShowActionToDismiss(false); // Optional
                properties.setSnackbarDismissActionText("OK"); // Optional

                noInternetSnackbarFire = builder.build();

            }
            break;
        }

        binding.fabGoBack.setOnClickListener((v) -> finish());

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (noInternetDialogPendulum != null) {
            noInternetDialogPendulum.show();
        }
        if (noInternetDialogSignal != null) {
            noInternetDialogSignal.show();
        }
        if (noInternetSnackbarFire != null) {
            noInternetSnackbarFire.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (noInternetDialogPendulum != null) {
            noInternetDialogPendulum.show();
        }
        if (noInternetDialogSignal != null) {
            noInternetDialogSignal.show();
        }
        if (noInternetSnackbarFire != null) {
            noInternetSnackbarFire.show();
        }

        binding.getRoot().postDelayed(() -> {
            if (noInternetDialogPendulum != null) {
                noInternetDialogPendulum.show();
            }
            if (noInternetDialogSignal != null) {
                noInternetDialogSignal.show();
            }
            if (noInternetSnackbarFire != null) {
                noInternetSnackbarFire.show();
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (noInternetDialogPendulum != null) {
            noInternetDialogPendulum.show();
        }
        if (noInternetDialogSignal != null) {
            noInternetDialogSignal.show();
        }
        if (noInternetSnackbarFire != null) {
            noInternetSnackbarFire.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (noInternetDialogPendulum != null) {
            noInternetDialogPendulum.show();
        }
        if (noInternetDialogSignal != null) {
            noInternetDialogSignal.show();
        }
        if (noInternetSnackbarFire != null) {
            noInternetSnackbarFire.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (noInternetDialogPendulum != null) {
            noInternetDialogPendulum.show();
        }
        if (noInternetDialogSignal != null) {
            noInternetDialogSignal.show();
        }
        if (noInternetSnackbarFire != null) {
            noInternetSnackbarFire.show();
        }
    }
}
