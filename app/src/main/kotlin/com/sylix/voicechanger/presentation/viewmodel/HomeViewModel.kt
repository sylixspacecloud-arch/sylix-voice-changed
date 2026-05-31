package com.sylix.voicechanger.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sylix.voicechanger.domain.audio.AudioEngine
import com.sylix.voicechanger.domain.audio.VoiceConversionProcessor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isRecording: Boolean = false,
    val currentVoice: String = "Realistic Female",
    val volumeLevel: Float = 0f,
    val latencyMs: Int = 0,
    val isDiscordRunning: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val audioEngine: AudioEngine,
    private val voiceProcessor: VoiceConversionProcessor
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            audioEngine.audioState.collect { audioState ->
                _uiState.value = _uiState.value.copy(
                    isRecording = audioState.isRecording,
                    currentVoice = audioState.currentPreset,
                    error = audioState.error
                )
            }
        }

        viewModelScope.launch {
            audioEngine.latencyMs.collect { latency ->
                _uiState.value = _uiState.value.copy(latencyMs = latency)
            }
        }

        viewModelScope.launch {
            audioEngine.volumeLevel.collect { volume ->
                _uiState.value = _uiState.value.copy(volumeLevel = volume)
            }
        }
    }

    fun startRecording() {
        viewModelScope.launch {
            audioEngine.startRecording()
        }
    }

    fun stopRecording() {
        viewModelScope.launch {
            audioEngine.stopRecording()
        }
    }

    fun selectVoice(voiceId: String) {
        viewModelScope.launch {
            audioEngine.setVoicePreset(voiceId)
            _uiState.value = _uiState.value.copy(currentVoice = voiceId)
        }
    }

    fun toggleRecording() {
        if (_uiState.value.isRecording) {
            stopRecording()
        } else {
            startRecording()
        }
    }
}
