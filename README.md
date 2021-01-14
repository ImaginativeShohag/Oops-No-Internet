# Oops! No Internet!

Simple no Internet dialogs and snackbar, which will automatically appear and disappear based on Internet connectivity status.

[![Developer](https://img.shields.io/badge/Maintainer-ImaginativeShohag-green)](https://github.com/ImaginativeShohag)
[![GitHub release](https://img.shields.io/github/release/ImaginativeShohag/Oops-No-Internet.svg)](https://github.com/ImaginativeShohag/Oops-No-Internet/releases)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Oops!%20No%20Internet!-green.svg?style=flat)]( https://android-arsenal.com/details/1/8005 )
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

## Previews

### Preview of `NoInternetDialogSignal`:

|                           Day Mode                           |                   Day Mode (Airplane Mode)                   |                          Night Mode                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![No Internet Dialog: Signal](https://github.com/ImaginativeShohag/Oops-No-Internet/blob/master/images/dialog_signal_1.gif) | ![No Internet Dialog: Signal](https://github.com/ImaginativeShohag/Oops-No-Internet/blob/master/images/dialog_signal_2.gif) | ![Airplane Mode Dialog: Signal](https://github.com/ImaginativeShohag/Oops-No-Internet/blob/master/images/dialog_signal_3.gif) |

### Preview of `NoInternetDialogPendulum`:

|                           Day Mode                           |                   Day Mode (Airplane Mode)                   |                          Night Mode                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![No Internet Dialog: Pendulum](https://github.com/ImaginativeShohag/Oops-No-Internet/blob/master/images/dialog_pendulum_1.gif) | ![No Internet Dialog: Pendulum](https://github.com/ImaginativeShohag/Oops-No-Internet/blob/master/images/dialog_pendulum_2.gif) | ![Airplane Mode Dialog: Pendulum](https://github.com/ImaginativeShohag/Oops-No-Internet/blob/master/images/dialog_pendulum_3.gif) |

### Preview of `NoInternetSnackbarFire`:

|                           Day Mode                           |                   Day Mode (Airplane Mode)                   |                          Night Mode                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![No Internet Snackbar: Fire](https://github.com/ImaginativeShohag/Oops-No-Internet/blob/master/images/snackbar_fire_1.png) | ![No Internet Dialog: Fire](https://github.com/ImaginativeShohag/Oops-No-Internet/blob/master/images/snackbar_fire_2.png) | ![Airplane Mode Snackbar: Fire](https://github.com/ImaginativeShohag/Oops-No-Internet/blob/master/images/snackbar_fire_3.png) |

## Types

The library provides `NoInternetObserveComponent`, a simple lifecycle component. The purpose of the component is to provide an easy way to notify about the  Internet connection connectivity change. It gives a simple interface to connect with any elements (`Dialog`, `Snackbar`, etc.) with ease.
The library currently provides two `Dialogs` and a `Snackbar` out of the box for no Internet notification.

### No Internet Dialogs

* `NoInternetDialogPendulum` (formerly just `NoInternetDialog`)
* `NoInternetDialogSignal`

### No Internet Snackbar

* `NoInternetSnackbarFire`

## Usage

### Dependency

#### Add the followings to your project level `build.gradle` file.

```groovy
android {

    // ...

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    // ...
    
    // Material Components for Android. Replace the version with the latest version of Material Components library.
    implementation 'com.google.android.material:material:1.2.1'

    implementation 'org.imaginativeworld.oopsnointernet:oopsnointernet:2.0.0'
}
```

**Note 0.** Minimum SDK for this library is **API 21** (Android 5.0 Lollipop).

**Note 1.** Your application have to use **AndroidX** to use this library.

**Note 2.** Your have to use **\*.MaterialComponents.\*** in you styles.

### Finally

Just initialize any of the `NoInternetDialog*` and/or `NoInternetSnackbar*` using the builder in `onCreate()`, thats all! :)

The `NoInternetDialog*` and/or `NoInternetSnackbar*` will then automatically appear when no active Internet connection found and disappear otherwise.

Customizable attributes with their default values are given in the following examples.

```kotlin
// Kotlin
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // ...

        // No Internet Dialog: Pendulum
		NoInternetDialogPendulum.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }

                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                "Check your Internet connection and try again." // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional

                onAirplaneModeTitle = "No Internet" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                pleaseTurnOffText = "Please turn off" // Optional
                airplaneModeOffButtonText = "Airplane mode" // Optional
                showAirplaneModeOffButtons = true // Optional
            }
        }.build()

        // No Internet Dialog: Signal
        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }

                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                "Check your Internet connection and try again." // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional

                onAirplaneModeTitle = "No Internet" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                pleaseTurnOffText = "Please turn off" // Optional
                airplaneModeOffButtonText = "Airplane mode" // Optional
                showAirplaneModeOffButtons = true // Optional
            }
        }.build()
        
        // No Internet Snackbar: Fire
        NoInternetSnackbarFire.Builder(
            binding.mainContainer,
            lifecycle
        ).apply {
            snackbarProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }

                duration = Snackbar.LENGTH_INDEFINITE // Optional
                noInternetConnectionMessage = "No active Internet connection!" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode!" // Optional
                snackbarActionText = "Settings" // Optional
                showActionToDismiss = false // Optional
                snackbarDismissActionText = "OK" // Optional
            }
        }.build()
    }
}
```

```java
// Java
public class Main2Activity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ...
        
        // No Internet Dialog: Pendulum
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

        builder.build();
        
        // No Internet Dialog: Signal
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

        builder.build();
        
		// No Internet Snackbar: Fire
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

        builder.build();
    }
}
```

The sample project can be found [here](https://github.com/ImaginativeShohag/Oops-No-Internet/tree/master/sample).

## Credits

This library is inspired by [NoInternetDialog](https://github.com/appwise-labs/NoInternetDialog).

Library name credit goes to Fahima Lamia Neha.

The icons and images used in the sample project are from freepik.com and flaticon.com.

## Changelog

### 2.0.0

We move our library from **jitpack.io** to **maven** repository. So no need to add any repositories for using the library.

We changed the file structures and re-right the whole library. So you have to do a little refactoring to use version 2 of the library. Just follow the samples, you will get it. Or add an issue if there any problem to migrate.

The library now provides a simple lifecycle component for observing the Internet connection changes. It can be used with any kind of custom elements.

We provide two out of the box dialogs and a snackbar implementation.

Notable other changes:

* **Changed:** Previous `NoInternetDialog` now `NoInternetDialogPendulum`.
* **New:** New `NoInternetDialogSignal` dialog added.
* **Removed:** `NoInternetSnackbar` removed.
* **New:** New `NoInternetSnackbarFire` added.

### 1.1.4 & 1.1.5

- `NoInternetUtils` functions are now documented.
- Dialog width optimized for small screens.
- Dependency updated.

### 1.1.3

`NoInternetUtils` class methods are now accessible independently.

### 1.1.2

Airplane animation tweak.

### 1.1.1

Small change in check internet.

### 1.1.0

\+ Airplane mode button added to the dialog.

\+ The snackbar text will be updated based on airplane mode.

### 1.0.1

Airplane mode animation added to the dialog and known bugs fixed.

### 1.0.0

The initial release of the library.

## Licence

<div>Icons made by <a href="https://www.flaticon.com/authors/darius-dan" title="Darius Dan">Darius Dan</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>

```
Copyright 2019 Md. Mahmudul Hasan Shohag

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

