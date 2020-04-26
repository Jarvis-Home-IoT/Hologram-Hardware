# Hologram Hardware
🌌 Hologram hardware core repository based on Android Things, IoT platform.

## What used?
- raspberry pi 3B Model
- 16GB sd-card
- 24 inch monitor

## How to setup OS
This is [how to setting](https://developer.android.com/things/hardware/raspberrypi) the Android Things OS on raspberry pi.

### Download
[Download](https://partner.android.com/things/console/#/tools) the AndroidThings OS image file.<br>
And [reference](https://www.raspberrypi.org/documentation/installation/installing-images/windows.md) on the raspberrypi website.

### display
To make the screen work on the Raspberry Pi 3, you’ll have to mount the Android Things SD card to your computer and modify the `config.txt` file to append the following lines:
```gradle
max_usb_current=1
hdmi_group=2
hdmi_mode=87
hdmi_cvt 800 480 60 6 0 0 0
```

### Install Application
Connect Ethernet from the computer to the raspberrypi, and use below command line. <br>
We should know the ip address from the connected monitor screen.
```
adb connect <ip>:5555
```

### How to manage and setting devices
Using [Android Things Toolkit](https://play.google.com/store/apps/details?id=com.google.android.things.companion) on Android device.<br>
This app is for Android Things developers who have Android Things kits or a Raspberry Pi 3 Model B development board. Visit http://androidthings.withgoogle.com/kits/ to learn more about purchasing options. <br><br>

## Setting and connect wifi
After install the Android Things Toolkit, you can wifi setting using the Android Things Toolkit application.<br>
- set up a device (Pair your device)
- input the device code
- wifi connecting
- test peripherals
- install application

## Network
For the communicate to the BirdParks-Exhibit application, client-backend using [Firebase Service](https://firebase.google.com/).

### Cloud Storage
[Cloud Storage](https://firebase.google.com/docs/storage/) is one of a service from the firebase systems.<br>
Cloud Storage is built for app developers who need to store and serve user-generated content, such as photos or videos.<br>
Cloud Storage for Firebase is a powerful, simple, and cost-effective object storage service built for Google scale. The Firebase SDKs for Cloud Storage add Google security to file uploads and downloads for your Firebase apps, regardless of network quality. You can use our SDKs to store images, audio, video, or other user-generated content. On the server, you can use Google Cloud Storage, to access the same files.

## [Runtime permissions](https://developer.android.com/things/get-started/platform-differences#permissions)
Declare permissions that you need in your app's manifest file.

The granting of app permissions is done differently for Android Things than for typical Android apps since many IoT applications do not require a user interface or input device. Permissions are granted using Android Studio or the Android Things Console.

When running an app from Android Studio, all permissions (including dangerous permissions) are granted at install time. This applies to new app installs and updated <uses-permission> elements in existing apps. You can use the adb tool to grant or revoke permissions for testing.

When you are ready to distribute your apps using the Android Things Console, you grant the dangerous permissions (instead of the end user) for all apps as part of the build creation process. You can override this during development, but not on actual products; end users cannot modify these permissions.

## Android Things GPIO
![screenshot1360812119](https://user-images.githubusercontent.com/24237865/68541251-6f1dc100-03e0-11ea-8447-c6fbcf7bc7ef.png)
https://developer.android.com/things/hardware/raspberrypi#io-pinout

# License
```xml
Copyright 2020 rurimo

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
