package com.sylix.voicechanger.domain.audio

import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AudioEngine {
    val audioState: StateFlow<AudioState>
    val latencyMs: StateFlow<Int>
    val volumeLevel: StateFlow<Float>

    suspend fun initialize()
    suspend fun startRecording()
    suspend fun stopRecording()
    suspend fun startPlayback()
    suspend fun stopPlayback()
    suspend fun release()
    suspend fun setVoicePreset(presetId: String)
}

data class AudioState(
    val isRecording: Boolean = false,
    val isProcessing: Boolean = false,
    val isPlaying: Boolean = false,
    val currentPreset: String = "realistic_female",
    val error: String? = null
)

class AudioEngineImpl : AudioEngine {
    private val _audioState = MutableStateFlow(AudioState())
    override val audioState: StateFlow<AudioState> = _audioState

    private val _latencyMs = MutableStateFlow(0)
    override val latencyMs: StateFlow<Int> = _latencyMs

    private val _volumeLevel = MutableStateFlow(0f)
    override val volumeLevel: StateFlow<Float> = _volumeLevel

    private var audioRecord: AudioRecord? = null
    private var audioTrack: AudioTrack? = null

    companion object {
        private const val SAMPLE_RATE = 48000
        private const val CHANNEL_CONFIG = android.media.AudioFormat.CHANNEL_IN_MONO
        private const val AUDIO_FORMAT = android.media.AudioFormat.ENCODING_PCM_16BIT
        private const val BUFFER_SIZE = 4096
    }

    override suspend fun initialize() {
        try {
            val bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT)
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                bufferSize
            )
            _audioState.value = _audioState.value.copy(error = null)
        } catch (e: Exception) {
            _audioState.value = _audioState.value.copy(error = "Failed to initialize audio: ${e.message}")
        }
    }

    override suspend fun startRecording() {
        try {
            audioRecord?.startRecording()
            _audioState.value = _audioState.value.copy(
                isRecording = true,
                isProcessing = true,
                error = null
            )
        } catch (e: Exception) {
            _audioState.value = _audioState.value.copy(error = "Failed to start recording: ${e.message}")
        }
    }

    override suspend fun stopRecording() {
        try {
            audioRecord?.stop()
            _audioState.value = _audioState.value.copy(
                isRecording = false,
                isProcessing = false
            )
        } catch (e: Exception) {
            _audioState.value = _audioState.value.copy(error = "Failed to stop recording: ${e.message}")
        }
    }

    override suspend fun startPlayback() {
        try {
            audioTrack?.play()
            _audioState.value = _audioState.value.copy(isPlaying = true)
        } catch (e: Exception) {
            _audioState.value = _audioState.value.copy(error = "Failed to start playback: ${e.message}")
        }
    }

    override suspend fun stopPlayback() {
        try {
            audioTrack?.stop()
            _audioState.value = _audioState.value.copy(isPlaying = false)
        } catch (e: Exception) {
            _audioState.value = _audioState.value.copy(error = "Failed to stop playback: ${e.message}")
        }
    }

    override suspend fun release() {
        try {
            audioRecord?.release()
            audioTrack?.release()
            audioRecord = null
            audioTrack = null
        } catch (e: Exception) {
            _audioState.value = _audioState.value.copy(error = "Failed to release audio: ${e.message}")
        }
    }

    override suspend fun setVoicePreset(presetId: String) {
        _audioState.value = _audioState.value.copy(currentPreset = presetId)
    }
}
