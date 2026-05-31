package com.sylix.voicechanger.data.model

data class VoicePreset(
    val id: String,
    val name: String,
    val category: VoiceCategory,
    val description: String,
    val modelPath: String,
    val isDownloaded: Boolean = false,
    val isFavorite: Boolean = false,
    val fileSize: Long = 0L,
    val latencyMs: Int = 0,
    val qualityRating: Float = 0f,
    val sampleAudioUrl: String = ""
)

enum class VoiceCategory {
    FEMALE,
    MALE,
    SPECIAL
}

object VoicePresetDefaults {
    val presets = listOf(
        VoicePreset(
            id = "realistic_female",
            name = "Realistic Female",
            category = VoiceCategory.FEMALE,
            description = "Highest possible realism with minimal artifacts",
            modelPath = "models/realistic_female.onnx",
            qualityRating = 5f
        ),
        VoicePreset(
            id = "soft_female",
            name = "Soft Female",
            category = VoiceCategory.FEMALE,
            description = "Gentle and soothing female voice",
            modelPath = "models/soft_female.onnx",
            qualityRating = 4.8f
        ),
        VoicePreset(
            id = "young_female",
            name = "Young Female",
            category = VoiceCategory.FEMALE,
            description = "Youthful and energetic female voice",
            modelPath = "models/young_female.onnx",
            qualityRating = 4.7f
        ),
        VoicePreset(
            id = "anime_girl",
            name = "Anime Girl",
            category = VoiceCategory.FEMALE,
            description = "Stylized anime-inspired female voice",
            modelPath = "models/anime_girl.onnx",
            qualityRating = 4.5f
        ),
        VoicePreset(
            id = "deep_female",
            name = "Deep Female",
            category = VoiceCategory.FEMALE,
            description = "Deep and resonant female voice",
            modelPath = "models/deep_female.onnx",
            qualityRating = 4.6f
        ),
        VoicePreset(
            id = "male",
            name = "Male",
            category = VoiceCategory.MALE,
            description = "Natural male voice",
            modelPath = "models/male.onnx",
            qualityRating = 4.8f
        ),
        VoicePreset(
            id = "deep_male",
            name = "Deep Male",
            category = VoiceCategory.MALE,
            description = "Deep and powerful male voice",
            modelPath = "models/deep_male.onnx",
            qualityRating = 4.7f
        ),
        VoicePreset(
            id = "kid",
            name = "Kid",
            category = VoiceCategory.SPECIAL,
            description = "Youthful child-like voice",
            modelPath = "models/kid.onnx",
            qualityRating = 4.4f
        ),
        VoicePreset(
            id = "robot",
            name = "Robot",
            category = VoiceCategory.SPECIAL,
            description = "Robotic and synthetic voice",
            modelPath = "models/robot.onnx",
            qualityRating = 4.2f
        ),
        VoicePreset(
            id = "demon",
            name = "Demon",
            category = VoiceCategory.SPECIAL,
            description = "Dark and ominous voice",
            modelPath = "models/demon.onnx",
            qualityRating = 4.3f
        )
    )
}
