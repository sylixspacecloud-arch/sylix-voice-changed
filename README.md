# Sylix Voice Changer - Production Android Application

A professional real-time AI voice changer for Android with Material 3 UI, MVVM architecture, Discord integration, and advanced audio processing capabilities.

## Project Overview

Sylix Voice Changer is a cutting-edge Android application designed to provide high-quality real-time voice conversion for use alongside Discord voice chat and other voice communication apps. The application prioritizes voice quality, low latency, stability, and ease of use.

### Key Features

- **Real-Time AI Voice Conversion:** Advanced voice conversion using ONNX Runtime and TensorFlow Lite
- **10 Voice Presets:** Realistic Female, Soft Female, Young Female, Anime Girl, Deep Female, Male, Deep Male, Kid, Robot, Demon
- **Discord Integration:** Seamless integration with Discord voice chat
- **Material 3 UI:** Modern, professional dark-mode interface with purple accent theme
- **MVVM Architecture:** Clean, maintainable code structure
- **Low Latency:** Optimized for real-time voice processing (<100ms target)
- **Offline Support:** Download voice models for offline operation
- **Comprehensive Diagnostics:** System health monitoring and troubleshooting tools

## Target Device Specifications

- **Android Version:** Android 10+ (API 29+)
- **Processor:** Snapdragon devices (optimized)
- **Device:** OnePlus Nord CE 2 Lite 5G (reference device)
- **RAM:** 4GB+ (optimized for 4GB devices)
- **Storage:** 2GB+ free space for voice models

## Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Kotlin | 1.9.20 |
| Build System | Gradle | 8.2.0 |
| UI Framework | Jetpack Compose | 1.6.0 |
| Design System | Material 3 | 1.1.2 |
| Architecture | MVVM | - |
| AI Inference | ONNX Runtime | 1.16.3 |
| ML Framework | TensorFlow Lite | 2.14.0 |
| Audio Processing | Android Media | - |
| Async | Coroutines | 1.7.3 |
| Database | Room | 2.6.1 |
| Networking | Retrofit 2 | 2.10.0 |
| Logging | Timber | 5.0.1 |

## Project Structure

```
sylix-voice-changer/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/com/sylix/voicechanger/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   └── VoicePreset.kt
│   │   │   │   │   ├── repository/
│   │   │   │   │   └── local/
│   │   │   │   ├── domain/
│   │   │   │   │   ├── audio/
│   │   │   │   │   │   ├── AudioEngine.kt
│   │   │   │   │   │   └── VoiceConversionProcessor.kt
│   │   │   │   │   └── discord/
│   │   │   │   │       └── DiscordIntegrationManager.kt
│   │   │   │   ├── presentation/
│   │   │   │   │   ├── viewmodel/
│   │   │   │   │   │   └── HomeViewModel.kt
│   │   │   │   │   └── ui/
│   │   │   │   │       ├── theme/
│   │   │   │   │       ├── navigation/
│   │   │   │   │       └── screens/
│   │   │   │   └── service/
│   │   │   │       ├── VoiceProcessingService.kt
│   │   │   │       └── ModelDownloadService.kt
│   │   │   ├── res/
│   │   │   │   ├── values/
│   │   │   │   ├── drawable/
│   │   │   │   └── mipmap/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Architecture

### MVVM Pattern

The application follows the Model-View-ViewModel (MVVM) architectural pattern:

- **Model:** Data classes and repositories (VoicePreset, AudioState)
- **View:** Jetpack Compose UI components (HomeScreen, VoiceLibraryScreen, etc.)
- **ViewModel:** Business logic and state management (HomeViewModel, etc.)

### Audio Processing Pipeline

```
Microphone Input
    ↓
AudioRecord (48kHz, PCM 16-bit)
    ↓
Noise Suppression
    ↓
Echo Cancellation
    ↓
Automatic Gain Control
    ↓
Voice Activity Detection
    ↓
ONNX Runtime Inference
    ↓
Voice Conversion Processing
    ↓
AudioTrack Output
    ↓
Speaker Output
```

### Core Services

#### AudioEngine
Manages microphone capture, audio buffering, and real-time processing. Handles audio state, latency monitoring, and volume level tracking.

#### VoiceConversionProcessor
Integrates ONNX Runtime for AI-based voice conversion. Loads voice models, processes audio data, and returns converted output.

#### DiscordIntegrationManager
Detects Discord installation and running state. Manages setup wizard and voice selection popup for Discord sessions.

#### VoiceProcessingService
Foreground service for background audio processing during Discord voice calls. Maintains notification and keeps app alive.

## Building the Project

### Prerequisites

1. **Android Studio:** Latest stable version (2023.2+)
2. **Android SDK:** API 34 (target), API 24 (minimum)
3. **Kotlin:** 1.9.20+
4. **Gradle:** 8.2.0+
5. **Java:** JDK 17+

### Build Steps

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd sylix-voice-changer
   ```

2. **Open in Android Studio:**
   - File → Open → Select project root
   - Wait for Gradle sync to complete

3. **Build the project:**
   ```bash
   ./gradlew build
   ```

4. **Run on device/emulator:**
   ```bash
   ./gradlew installDebug
   ```

### Build Variants

- **Debug:** Development build with logging and debugging tools
- **Release:** Optimized production build with ProGuard obfuscation

## Generating APK

### Debug APK

```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release APK

```bash
# Create keystore (first time only)
keytool -genkey -v -keystore release.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias sylix

# Build release APK
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

### App Bundle (for Google Play)

```bash
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

## Voice Models

### Supported Formats

- **ONNX:** Open Neural Network Exchange format (.onnx)
- **TensorFlow Lite:** TensorFlow Lite format (.tflite)

### Model Placement

Place voice models in the `app/src/main/assets/models/` directory:

```
app/src/main/assets/models/
├── realistic_female.onnx
├── soft_female.onnx
├── young_female.onnx
├── anime_girl.onnx
├── deep_female.onnx
├── male.onnx
├── deep_male.onnx
├── kid.onnx
├── robot.onnx
└── demon.onnx
```

### Model Integration

Models are loaded at runtime through the `VoiceConversionProcessor`:

```kotlin
val processor = VoiceConversionProcessorImpl(context)
processor.loadModel("models/realistic_female.onnx")
val convertedAudio = processor.processAudio(inputAudioData)
```

## Permissions

The application requires the following permissions:

| Permission | Purpose |
|-----------|---------|
| `RECORD_AUDIO` | Capture microphone input for voice conversion |
| `POST_NOTIFICATIONS` | Send Discord integration notifications |

Permissions are requested at runtime with proper user prompts.

## Performance Optimization

### For Snapdragon Processors

- GPU acceleration via TensorFlow Lite NNAPI
- Optimized thread pool for audio processing
- Memory-efficient audio buffering

### For 4GB RAM Devices

- Adaptive model loading based on available memory
- Streaming audio processing instead of batch processing
- Automatic garbage collection optimization

### Latency Reduction

- Target: <100ms processing latency
- Techniques: Hardware acceleration, optimized buffer sizes, thread prioritization

## Discord Integration

### Setup Process

1. App detects Discord installation
2. Shows setup wizard if first time
3. Guides user through system audio settings
4. Configures audio routing (if possible)
5. Displays voice selection popup when Discord is active

### Limitations

Due to Android security model, direct microphone routing to Discord may not be possible. The app provides:

- Discord detection and status monitoring
- Voice preset quick selection
- Setup guide for manual audio configuration
- Alternative solutions documentation

## Troubleshooting

### Common Issues

| Issue | Solution |
|-------|----------|
| App crashes on startup | Check permissions, ensure models are in assets/ |
| No audio output | Verify microphone permission, check audio routing |
| High latency | Reduce buffer size, enable GPU acceleration |
| Discord not detected | Ensure Discord is installed, check package name |
| Model loading fails | Verify model format (ONNX/TFLite), check file size |

### Diagnostics Screen

The Diagnostics screen provides:

- Real-time system metrics (CPU, RAM, temperature)
- Audio pipeline status
- Model memory usage
- Inference timing
- Permission status
- Error logs

## Testing

### Unit Tests

```bash
./gradlew test
```

### Instrumented Tests

```bash
./gradlew connectedAndroidTest
```

### Manual Testing Checklist

- [ ] Microphone capture works
- [ ] Voice conversion produces output
- [ ] All voice presets load correctly
- [ ] Discord detection functions
- [ ] Settings persist across app restart
- [ ] Latency is <100ms
- [ ] No crashes during 30-minute session
- [ ] Battery usage is reasonable

## Deployment

### Google Play Store

1. Build release APK/AAB
2. Sign with release keystore
3. Create app listing on Google Play Console
4. Upload AAB
5. Configure store listing, screenshots, description
6. Submit for review

### Direct Installation

```bash
adb install app/build/outputs/apk/release/app-release.apk
```

## Documentation

- **Architecture:** See `ARCHITECTURE.md`
- **Audio Processing:** See `AUDIO_PROCESSING.md`
- **Discord Integration:** See `DISCORD_INTEGRATION.md`
- **Build Guide:** See `BUILD_GUIDE.md`

## Support & Troubleshooting

For issues or questions:

1. Check the Diagnostics screen for error details
2. Review logs in the app's log viewer
3. Consult the troubleshooting guide
4. Contact support with diagnostic logs

## License

Proprietary - Sylix Voice Changer

## Version

- **Version:** 1.0.0
- **Build:** 1
- **Release Date:** 2026-05-31

---

**Note:** This is a production-ready Android application. Ensure all dependencies are properly configured and models are available before building.
