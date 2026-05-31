package com.sylix.voicechanger.domain.audio

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.nio.FloatBuffer

interface VoiceConversionProcessor {
    val processingState: StateFlow<ProcessingState>
    suspend fun loadModel(modelPath: String): Boolean
    suspend fun processAudio(audioData: FloatArray): FloatArray?
    suspend fun unloadModel()
}

data class ProcessingState(
    val isModelLoaded: Boolean = false,
    val isProcessing: Boolean = false,
    val error: String? = null
)

class VoiceConversionProcessorImpl(private val context: Context) : VoiceConversionProcessor {
    private val _processingState = MutableStateFlow(ProcessingState())
    override val processingState: StateFlow<ProcessingState> = _processingState

    private var ortSession: OrtSession? = null
    private val ortEnv: OrtEnvironment = OrtEnvironment.getEnvironment()

    override suspend fun loadModel(modelPath: String): Boolean {
        return try {
            _processingState.value = _processingState.value.copy(isProcessing = true)

            val modelBytes = context.assets.open(modelPath).use { it.readBytes() }
            ortSession = ortEnv.createSession(modelBytes)

            _processingState.value = _processingState.value.copy(
                isModelLoaded = true,
                isProcessing = false,
                error = null
            )
            true
        } catch (e: Exception) {
            Timber.e(e, "Failed to load ONNX model: $modelPath")
            _processingState.value = _processingState.value.copy(
                isModelLoaded = false,
                isProcessing = false,
                error = "Failed to load model: ${e.message}"
            )
            false
        }
    }

    override suspend fun processAudio(audioData: FloatArray): FloatArray? {
        return try {
            if (ortSession == null) {
                Timber.w("Model not loaded, cannot process audio")
                return null
            }

            _processingState.value = _processingState.value.copy(isProcessing = true)

            // Create input tensor
            val inputTensor = OnnxTensor.createTensor(
                ortEnv,
                FloatBuffer.wrap(audioData),
                longArrayOf(1, audioData.size.toLong())
            )

            // Run inference
            val results = ortSession?.run(mapOf("input" to inputTensor))
            inputTensor.close()

            // Extract output
            val outputTensor = results?.get("output") as? OnnxTensor
            val outputData = outputTensor?.floatBuffer?.array() ?: FloatArray(0)
            outputTensor?.close()

            _processingState.value = _processingState.value.copy(isProcessing = false)
            outputData
        } catch (e: Exception) {
            Timber.e(e, "Error processing audio")
            _processingState.value = _processingState.value.copy(
                isProcessing = false,
                error = "Processing error: ${e.message}"
            )
            null
        }
    }

    override suspend fun unloadModel() {
        try {
            ortSession?.close()
            ortSession = null
            _processingState.value = _processingState.value.copy(isModelLoaded = false)
        } catch (e: Exception) {
            Timber.e(e, "Error unloading model")
        }
    }
}
