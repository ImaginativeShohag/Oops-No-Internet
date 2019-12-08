# Oops! No Internet!

A simple no Internet dialog and snackbar, which will automatically 
appear and disappear based on Internet connectivity status.

[![](https://jitpack.io/v/ImaginativeShohag/Oops-No-Internet.svg)](https://jitpack.io/#ImaginativeShohag/Oops-No-Internet)

## Usage

### Dependency

#### Step 1. Add the JitPack repository to your build file

Add it to your root **build.gradle** at the end of repositories:

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### Step 2. Add the dependency

```groovy
dependencies {
    // Material Components for Android
    implementation 'com.google.android.material:material:1.0.0'

    implementation 'com.github.ImaginativeShohag:Oops-No-Internet:1.0.0'
}
```

**Note 0.** Minimum SDK for this library is API 21 (Android 5.0 Lollipop).

**Note 1.** Your application have to use **AndroidX** to use this library.

**Note 2.** Your have to use **\*.MaterialComponents.\*** in you styles.

### Finally

To use the `NoInternetDialog` and/or `NoInternetSnackbar`, use the builder and initialize it in `onResume()` and finally 
destroy it in `onPause()`.

The `NoInternetDialog` and/or `NoInternetSnackbar` will then automatically appear when no active Internet connection found and disappear otherwise.

Customizable attributes with their default values are given in the following example.

```kotlin
// Kotlin
class MainActivity : AppCompatActivity() {

    // No Internet Dialog
    private var noInternetDialog: NoInternetDialog? = null

    // No Internet Snackbar
    private var noInternetSnackbar: NoInternetSnackbar? = null

    // ...

    override fun onResume() {
        super.onResume()

        // No Internet Dialog
        noInternetDialog = NoInternetDialog.Builder(this)
            .apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }
                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                    "Check your Internet connection and try again" // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional
            }
            .build()

        // No Internet Snackbar
        noInternetSnackbar =
            NoInternetSnackbar.Builder(this, findViewById(android.R.id.content))
                .apply {
                    connectionCallback = object : ConnectionCallback { // Optional
                        override fun hasActiveConnection(hasActiveConnection: Boolean) {
                            // ...
                        }
                    }
                    indefinite = true // Optional
                    noInternetConnectionMessage = "No active Internet connection!" // Optional
                    snackbarActionText = "Settings" // Optional
                    showActionToDismiss = false // Optional
                    snackbarDismissActionText = "OK" // Optional
                }
                .build()
    }

    override fun onPause() {
        super.onPause()

        // No Internet Dialog
        noInternetDialog?.destroy()

        // No Internet Snackbar
        noInternetSnackbar?.destroy()
    }
}
```

```java
// Java
public class Main2Activity extends AppCompatActivity {

    // No Internet Dialog
    NoInternetDialog noInternetDialog;

    // No Internet Snackbar
    NoInternetSnackbar noInternetSnackbar;

    // ...
    
    @Override
    protected void onResume() {
        super.onResume();

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

        noInternetDialog = builder1.build();


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
        builder2.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        builder2.setSnackbarActionText("Settings");
        builder2.setShowActionToDismiss(false);
        builder2.setSnackbarDismissActionText("OK");

        noInternetSnackbar = builder2.build();
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
```


## Inspiration

This library is inspired by [NoInternetDialog](https://github.com/appwise-labs/NoInternetDialog).

## Changelog

### 1.0.0

The initial release of the library.

## Licence

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

