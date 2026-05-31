# Sylix Voice Changer - Build Guide

## Prerequisites

Before building the Sylix Voice Changer application, ensure you have the following installed:

### Required Software

| Software | Version | Purpose |
|----------|---------|---------|
| Android Studio | 2023.2+ | IDE and build tools |
| Android SDK | API 34 | Target SDK |
| Android SDK | API 24+ | Minimum SDK |
| Kotlin | 1.9.20+ | Language |
| Gradle | 8.2.0+ | Build system |
| Java JDK | 17+ | Java runtime |
| Git | Latest | Version control |

### System Requirements

- **OS:** Windows 10+, macOS 10.14+, or Linux (Ubuntu 18.04+)
- **RAM:** 8GB minimum (16GB recommended)
- **Disk Space:** 10GB minimum for SDK and build artifacts
- **Internet:** Required for dependency downloads

## Setup Instructions

### 1. Install Android Studio

#### Windows/macOS
1. Download from [developer.android.com](https://developer.android.com/studio)
2. Run installer and follow prompts
3. Complete initial setup wizard

#### Linux (Ubuntu)
```bash
sudo apt-get update
sudo apt-get install android-studio
```

### 2. Configure Android SDK

1. Open Android Studio
2. Go to **Tools → SDK Manager**
3. Install required SDKs:
   - Android SDK Platform 34 (target)
   - Android SDK Platform 24-33 (support)
   - Android SDK Build-Tools 34.0.0
   - Android Emulator (optional)
   - Android SDK Platform-Tools

### 3. Set Environment Variables

#### Windows
```batch
set ANDROID_HOME=C:\Users\YourUsername\AppData\Local\Android\Sdk
set PATH=%PATH%;%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools
```

#### macOS/Linux
```bash
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

Add to `~/.bashrc` or `~/.zshrc` for persistence.

### 4. Clone Repository

```bash
git clone <repository-url>
cd sylix-voice-changer
```

### 5. Verify Gradle Wrapper

```bash
./gradlew --version
# Should output: Gradle 8.2.0
```

## Building the Project

### Debug Build

#### Via Android Studio

1. Open project in Android Studio
2. Wait for Gradle sync to complete
3. Select **Build → Make Project**
4. Or press `Ctrl+F9` (Windows/Linux) or `Cmd+F9` (macOS)

#### Via Command Line

```bash
./gradlew build
```

### Debug APK

```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release Build

#### Create Keystore (First Time Only)

```bash
keytool -genkey -v -keystore release.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 -alias sylix
```

**Prompts:**
- Keystore password: (create strong password)
- Key password: (same as keystore password)
- First and last name: Sylix Voice Changer
- Organizational unit: Development
- Organization: Sylix
- City/Locality: (your city)
- State/Province: (your state)
- Country code: (your country code)

#### Build Release APK

```bash
./gradlew assembleRelease \
  -Pandroid.injected.signing.store.file=release.keystore \
  -Pandroid.injected.signing.store.password=<password> \
  -Pandroid.injected.signing.key.alias=sylix \
  -Pandroid.injected.signing.key.password=<password>
```

**Output:** `app/build/outputs/apk/release/app-release.apk`

#### Build App Bundle (for Google Play)

```bash
./gradlew bundleRelease \
  -Pandroid.injected.signing.store.file=release.keystore \
  -Pandroid.injected.signing.store.password=<password> \
  -Pandroid.injected.signing.key.alias=sylix \
  -Pandroid.injected.signing.key.password=<password>
```

**Output:** `app/build/outputs/bundle/release/app-release.aab`

## Installing on Device

### Prerequisites

- Android device with USB debugging enabled
- USB cable connected to computer
- ADB (Android Debug Bridge) installed

### Enable USB Debugging

1. Open **Settings** on Android device
2. Go to **About Phone**
3. Tap **Build Number** 7 times
4. Go back to **Settings → Developer Options**
5. Enable **USB Debugging**

### Install APK

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Uninstall APK

```bash
adb uninstall com.sylix.voicechanger
```

## Testing the Build

### Unit Tests

```bash
./gradlew test
```

### Instrumented Tests (on device/emulator)

```bash
./gradlew connectedAndroidTest
```

### Run on Emulator

1. Open **Tools → Device Manager** in Android Studio
2. Create or select an emulator
3. Click play button to start
4. Select **Run → Run 'app'**

## Troubleshooting

### Common Build Errors

#### Error: "SDK location not found"

**Solution:**
```bash
echo "sdk.dir=/path/to/android/sdk" > local.properties
```

#### Error: "Gradle sync failed"

**Solution:**
```bash
./gradlew clean
./gradlew sync
```

#### Error: "Could not find android.jar"

**Solution:**
1. Open SDK Manager
2. Install missing Android SDK Platform
3. Run `./gradlew clean build`

#### Error: "Execution failed for task ':app:mergeDebugResources'"

**Solution:**
```bash
./gradlew clean
./gradlew build
```

#### Error: "ONNX Runtime not found"

**Solution:**
Ensure `build.gradle.kts` includes:
```kotlin
implementation("com.microsoft.onnxruntime:onnxruntime-android:1.16.3")
```

### Performance Issues

#### Slow Gradle Sync

```bash
# Increase Gradle heap size
echo "org.gradle.jvmargs=-Xmx4096m" >> gradle.properties
```

#### Slow Build Times

```bash
# Enable parallel builds
echo "org.gradle.parallel=true" >> gradle.properties

# Enable build cache
echo "org.gradle.caching=true" >> gradle.properties
```

### Device Connection Issues

```bash
# List connected devices
adb devices

# Restart ADB daemon
adb kill-server
adb start-server

# Check device logs
adb logcat
```

## Build Variants

### Debug Variant

- Debugging enabled
- ProGuard disabled
- Logging enabled
- Faster build time

### Release Variant

- Debugging disabled
- ProGuard enabled (code obfuscation)
- Optimized for size and performance
- Signed with release keystore

## Build Configuration

### gradle.properties

```properties
# Gradle settings
org.gradle.jvmargs=-Xmx4096m
org.gradle.parallel=true
org.gradle.caching=true

# Kotlin settings
kotlin.code.style=official
```

### local.properties

```properties
# Android SDK location (auto-generated)
sdk.dir=/path/to/android/sdk
```

## Continuous Integration

### GitHub Actions Example

```yaml
name: Build

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: 17
      - run: ./gradlew build
      - run: ./gradlew assembleDebug
```

## Distribution

### Google Play Store

1. Build release APK/AAB
2. Sign with release keystore
3. Create app listing on Google Play Console
4. Upload AAB
5. Fill store listing details
6. Submit for review

### Direct Distribution

1. Build release APK
2. Host on server or file sharing service
3. Provide download link to users
4. Users install via `adb install` or file manager

### Beta Testing

1. Build release APK
2. Upload to Firebase App Distribution
3. Invite testers
4. Collect feedback

## Signing Configuration

### Automatic Signing (Recommended)

In Android Studio:
1. **Build → Generate Signed Bundle/APK**
2. Select **APK**
3. Click **Next**
4. Create or select keystore
5. Fill key details
6. Select **Release** build type
7. Click **Finish**

### Manual Signing

```bash
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore release.keystore \
  app-release-unsigned.apk sylix

zipalign -v 4 app-release-unsigned.apk app-release.apk
```

## Optimization Tips

### Reduce APK Size

```kotlin
// In build.gradle.kts
android {
    bundle {
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }
}
```

### Enable ProGuard

```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

### Optimize Dependencies

```bash
./gradlew dependencies --configuration releaseRuntimeClasspath
```

## Useful Commands

| Command | Purpose |
|---------|---------|
| `./gradlew build` | Build all variants |
| `./gradlew clean` | Clean build artifacts |
| `./gradlew assembleDebug` | Build debug APK |
| `./gradlew assembleRelease` | Build release APK |
| `./gradlew bundleRelease` | Build app bundle |
| `./gradlew test` | Run unit tests |
| `./gradlew connectedAndroidTest` | Run instrumented tests |
| `./gradlew lint` | Run lint checks |
| `./gradlew dependencies` | Show dependency tree |
| `adb install app/build/outputs/apk/debug/app-debug.apk` | Install on device |

## Next Steps

1. Review `README.md` for project overview
2. Read `ARCHITECTURE.md` for code structure
3. Check `AUDIO_PROCESSING.md` for audio pipeline details
4. Review `DISCORD_INTEGRATION.md` for Discord setup

---

**For additional help, consult the official Android documentation:**
- [Android Developer Guide](https://developer.android.com/guide)
- [Gradle Plugin Documentation](https://developer.android.com/studio/build)
- [Kotlin Documentation](https://kotlinlang.org/docs)
