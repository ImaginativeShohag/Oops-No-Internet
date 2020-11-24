package org.imaginativeworld.oopsnointernet.sample;


import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;
import org.imaginativeworld.oopsnointernet.NoInternetSnackbar;
import org.imaginativeworld.oopsnointernet.sample.databinding.ActivityJavaBinding;

public class JavaActivity extends AppCompatActivity {

    private ActivityJavaBinding binding;

    // No Internet Dialog
    private NoInternetDialog noInternetDialog;

    // No Internet Snackbar
    private NoInternetSnackbar noInternetSnackbar;

    private int selectedRadioId = R.id.radio_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                selectedRadioId = checkedId;

                init();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    private void init() {

        switch (selectedRadioId) {

            case R.id.radio_dialog:

                if (noInternetSnackbar != null) {
                    noInternetSnackbar.destroy();
                }

                // No Internet Dialog
                NoInternetDialog.Builder builder1 = new NoInternetDialog.Builder(this);

                builder1.setConnectionCallback(new ConnectionCallback() { // Optional
                    @Override
                    public void hasActiveConnection(boolean hasActiveConnection) {
                        // ...
                    }
                });
                builder1.setCancelable(false); // Optional
                builder1.setNoInternetConnectionTitle("No Internet"); // Optional
                builder1.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
                builder1.setShowInternetOnButtons(true); // Optional
                builder1.setPleaseTurnOnText("Please turn on"); // Optional
                builder1.setWifiOnButtonText("Wifi"); // Optional
                builder1.setMobileDataOnButtonText("Mobile data"); // Optional

                builder1.setOnAirplaneModeTitle("No Internet"); // Optional
                builder1.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
                builder1.setPleaseTurnOffText("Please turn off"); // Optional
                builder1.setAirplaneModeOffButtonText("Airplane mode"); // Optional
                builder1.setShowAirplaneModeOffButtons(true); // Optional

                noInternetDialog = builder1.build();

                break;

            case R.id.radio_snackbar:

                if (noInternetDialog != null) {
                    noInternetDialog.destroy();
                }

                // No Internet Snackbar
                NoInternetSnackbar.Builder builder2 = new NoInternetSnackbar.Builder(this, (ViewGroup) findViewById(android.R.id.content));

                builder2.setConnectionCallback(new ConnectionCallback() { // Optional
                    @Override
                    public void hasActiveConnection(boolean hasActiveConnection) {
                        // ...
                    }
                });
                builder2.setIndefinite(true); // Optional
                builder2.setNoInternetConnectionMessage("No active Internet connection!"); // Optional
                builder2.setOnAirplaneModeMessage("You have turned on the airplane mode!"); // Optional
                builder2.setSnackbarActionText("Settings");
                builder2.setShowActionToDismiss(false);
                builder2.setSnackbarDismissActionText("OK");

                noInternetSnackbar = builder2.build();

                break;

        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        // No Internet Dialog
        if (noInternetDialog != null) {
            noInternetDialog.destroy();
        }

        // No Internet Snackbar
        if (noInternetSnackbar != null) {
            noInternetSnackbar.destroy();
        }
    }
}
