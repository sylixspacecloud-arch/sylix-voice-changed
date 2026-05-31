# Sylix Voice Changer - Architecture Documentation

## Overview

Sylix Voice Changer follows a clean, layered architecture with clear separation of concerns. The application is built using MVVM (Model-View-ViewModel) pattern combined with domain-driven design principles.

## Architecture Layers

### 1. Presentation Layer (UI)

**Location:** `app/src/main/kotlin/com/sylix/voicechanger/presentation/`

Responsible for displaying UI and handling user interactions.

**Components:**
- **Screens:** Jetpack Compose UI components for each feature (HomeScreen, VoiceLibraryScreen, etc.)
- **ViewModels:** Business logic and state management (HomeViewModel, SettingsViewModel, etc.)
- **Theme:** Material 3 design tokens and color scheme
- **Navigation:** Tab-based navigation with Compose Navigation

**Key Classes:**
- `HomeScreen` - Main interface with microphone button
- `VoiceLibraryScreen` - Browse voice presets
- `LiveTestScreen` - Record and compare audio
- `DiscordIntegrationScreen` - Discord setup and voice selection
- `DownloadsScreen` - Model management
- `SettingsScreen` - App configuration
- `DiagnosticsScreen` - System monitoring

### 2. Domain Layer

**Location:** `app/src/main/kotlin/com/sylix/voicechanger/domain/`

Contains business logic and use cases. Independent of Android framework.

**Submodules:**

#### Audio Domain (`domain/audio/`)
- `AudioEngine` - Interface for audio capture and playback
- `AudioEngineImpl` - Implementation using Android AudioRecord/AudioTrack
- `VoiceConversionProcessor` - ONNX Runtime integration
- `ProcessingState` - Audio processing state management

#### Discord Domain (`domain/discord/`)
- `DiscordIntegrationManager` - Discord app detection and integration
- `DiscordState` - Discord connection state

### 3. Data Layer

**Location:** `app/src/main/kotlin/com/sylix/voicechanger/data/`

Manages data sources and repositories.

**Submodules:**

#### Models (`data/model/`)
- `VoicePreset` - Voice preset data structure
- `VoiceCategory` - Voice categorization enum
- `VoicePresetDefaults` - Default voice presets

#### Repositories (`data/repository/`)
- `VoiceRepository` - Voice preset data access
- `SettingsRepository` - User preferences

#### Local Storage (`data/local/`)
- Room database for voice metadata
- SharedPreferences for app settings
- File system for model caching

### 4. Service Layer

**Location:** `app/src/main/kotlin/com/sylix/voicechanger/service/`

Background services for long-running operations.

**Services:**
- `VoiceProcessingService` - Foreground service for real-time audio processing
- `ModelDownloadService` - Background model downloading

## Data Flow

### Voice Conversion Flow

```
User Input (Microphone)
    ↓
AudioEngine.startRecording()
    ↓
AudioRecord captures PCM data
    ↓
Audio Processing Pipeline:
    - Noise Suppression
    - Echo Cancellation
    - Automatic Gain Control
    - Voice Activity Detection
    ↓
VoiceConversionProcessor.processAudio()
    ↓
ONNX Runtime Inference
    ↓
Voice Model Processing
    ↓
Converted Audio Output
    ↓
AudioTrack.write()
    ↓
Speaker Output
```

### State Management Flow

```
User Action (e.g., tap microphone)
    ↓
ViewModel.toggleRecording()
    ↓
AudioEngine.startRecording()
    ↓
AudioState updated
    ↓
StateFlow emits new state
    ↓
Compose recomposes UI
    ↓
UI reflects new state
```

## Component Interactions

### HomeViewModel & AudioEngine

```
HomeViewModel
    ├─ Collects audioState from AudioEngine
    ├─ Collects latencyMs from AudioEngine
    ├─ Collects volumeLevel from AudioEngine
    └─ Calls AudioEngine methods:
        ├─ startRecording()
        ├─ stopRecording()
        ├─ setVoicePreset()
        └─ release()
```

### AudioEngine & VoiceConversionProcessor

```
AudioEngine
    ├─ Captures audio via AudioRecord
    ├─ Calls VoiceConversionProcessor.processAudio()
    ├─ Receives converted audio
    └─ Outputs via AudioTrack
```

### DiscordIntegrationManager

```
DiscordIntegrationManager
    ├─ Checks Discord installation via PackageManager
    ├─ Monitors Discord running state via ActivityManager
    ├─ Manages Discord setup wizard
    └─ Emits DiscordState updates
```

## State Management

### Audio State

```kotlin
data class AudioState(
    val isRecording: Boolean = false,
    val isProcessing: Boolean = false,
    val isPlaying: Boolean = false,
    val currentPreset: String = "realistic_female",
    val error: String? = null
)
```

### Processing State

```kotlin
data class ProcessingState(
    val isModelLoaded: Boolean = false,
    val isProcessing: Boolean = false,
    val error: String? = null
)
```

### Discord State

```kotlin
data class DiscordState(
    val isInstalled: Boolean = false,
    val isRunning: Boolean = false,
    val isSetupComplete: Boolean = false,
    val error: String? = null
)
```

## Threading Model

### Main Thread
- UI rendering (Compose)
- ViewModel state updates
- User interaction handling

### Audio Thread
- AudioRecord reading (real-time priority)
- Audio processing
- AudioTrack writing

### Background Thread
- ONNX Runtime inference
- Model loading
- File I/O operations

**Implementation:** Kotlin Coroutines with appropriate dispatchers

```kotlin
viewModelScope.launch(Dispatchers.Main) {
    // UI updates
}

viewModelScope.launch(Dispatchers.Default) {
    // CPU-intensive work
}

viewModelScope.launch(Dispatchers.IO) {
    // File/Network I/O
}
```

## Dependency Injection

**Current:** Manual dependency creation

**Future:** Hilt dependency injection framework

```kotlin
// Example with Hilt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val audioEngine: AudioEngine,
    private val voiceProcessor: VoiceConversionProcessor
) : ViewModel()
```

## Error Handling

### Error Propagation

```
Exception in AudioEngine
    ↓
AudioState.error updated
    ↓
StateFlow emits error
    ↓
ViewModel receives error
    ↓
UI displays error message
```

### Error Recovery

1. **Audio Interruption:** Automatically restart audio capture
2. **Model Loading Failure:** Show error, allow retry
3. **Permission Denied:** Show permission request dialog
4. **Discord Detection Failure:** Gracefully degrade to manual setup

## Performance Considerations

### Audio Processing Latency

- **Target:** <100ms end-to-end
- **Components:**
  - AudioRecord buffering: ~20ms
  - Audio processing: ~30ms
  - ONNX inference: ~40ms
  - AudioTrack playback: ~10ms

### Memory Management

- **Model Loading:** Load only current voice model
- **Audio Buffering:** Fixed-size circular buffers
- **Garbage Collection:** Minimize allocations in audio thread

### CPU Optimization

- **Hardware Acceleration:** GPU via TensorFlow Lite NNAPI
- **Thread Prioritization:** Audio thread at high priority
- **Batch Processing:** Process multiple frames together

## Testing Strategy

### Unit Tests

- ViewModel logic
- Audio state transitions
- Discord detection logic
- Data model validation

### Integration Tests

- AudioEngine with mock audio data
- VoiceConversionProcessor with test models
- DiscordIntegrationManager with mock PackageManager

### UI Tests

- Screen rendering
- User interaction handling
- Navigation between screens

## Scalability

### Adding New Voice Presets

1. Add to `VoicePresetDefaults.presets`
2. Place model in `assets/models/`
3. Update UI to display new preset

### Adding New Screens

1. Create screen Composable in `presentation/ui/screens/`
2. Create corresponding ViewModel
3. Add to navigation
4. Update tab bar

### Adding New Audio Processing

1. Extend `AudioEngine` interface
2. Implement in `AudioEngineImpl`
3. Integrate into audio pipeline
4. Update state management

## Security Considerations

- **Model Protection:** ProGuard obfuscation
- **Audio Data:** Processed in-memory, not persisted
- **Permissions:** Requested at runtime with user consent
- **Discord Integration:** No sensitive data storage

## Future Improvements

1. **Hilt Dependency Injection:** Replace manual DI
2. **Room Database:** Persistent storage for settings
3. **WorkManager:** Scheduled model updates
4. **ML Kit Integration:** Additional ML capabilities
5. **Cloud Sync:** Cross-device settings sync
6. **Advanced Audio Processing:** Custom DSP filters
7. **Real-time Metrics:** Performance monitoring dashboard

---

This architecture provides a solid foundation for a production-ready Android application with clear separation of concerns, testability, and maintainability.
