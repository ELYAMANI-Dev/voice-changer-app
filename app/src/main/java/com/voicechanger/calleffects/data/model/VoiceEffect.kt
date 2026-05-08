package com.voicechanger.calleffects.data.model

import androidx.compose.ui.graphics.Color

enum class VoiceEffectType {
    NORMAL,
    GIRL,
    MAN,
    WOMAN,
    CHILD,
    OLD_MAN,
    ROBOT,
    ALIEN,
    DEVIL,
    GIANT
}

data class VoiceEffect(
    val type: VoiceEffectType,
    val nameRes: Int,
    val iconEmoji: String,
    val pitchShift: Float,   // playback pitch multiplier (0.5 = half pitch, 2.0 = double)
    val speedShift: Float,   // playback speed multiplier
    val color: Color
)

object VoiceEffects {
    val all = listOf(
        VoiceEffect(
            type = VoiceEffectType.NORMAL,
            nameRes = 0,
            iconEmoji = "🎤",
            pitchShift = 1.0f,
            speedShift = 1.0f,
            color = Color(0xFF607D8B)
        ),
        VoiceEffect(
            type = VoiceEffectType.GIRL,
            nameRes = 0,
            iconEmoji = "👧",
            pitchShift = 1.6f,
            speedShift = 1.1f,
            color = Color(0xFFE91E63)
        ),
        VoiceEffect(
            type = VoiceEffectType.MAN,
            nameRes = 0,
            iconEmoji = "👨",
            pitchShift = 0.75f,
            speedShift = 0.95f,
            color = Color(0xFF1565C0)
        ),
        VoiceEffect(
            type = VoiceEffectType.WOMAN,
            nameRes = 0,
            iconEmoji = "👩",
            pitchShift = 1.2f,
            speedShift = 1.0f,
            color = Color(0xFF9C27B0)
        ),
        VoiceEffect(
            type = VoiceEffectType.CHILD,
            nameRes = 0,
            iconEmoji = "🧒",
            pitchShift = 1.8f,
            speedShift = 1.15f,
            color = Color(0xFFFF9800)
        ),
        VoiceEffect(
            type = VoiceEffectType.OLD_MAN,
            nameRes = 0,
            iconEmoji = "👴",
            pitchShift = 0.6f,
            speedShift = 0.85f,
            color = Color(0xFF795548)
        ),
        VoiceEffect(
            type = VoiceEffectType.ROBOT,
            nameRes = 0,
            iconEmoji = "🤖",
            pitchShift = 0.9f,
            speedShift = 1.0f,
            color = Color(0xFF009688)
        ),
        VoiceEffect(
            type = VoiceEffectType.ALIEN,
            nameRes = 0,
            iconEmoji = "👽",
            pitchShift = 2.0f,
            speedShift = 1.3f,
            color = Color(0xFF4CAF50)
        ),
        VoiceEffect(
            type = VoiceEffectType.DEVIL,
            nameRes = 0,
            iconEmoji = "😈",
            pitchShift = 0.5f,
            speedShift = 0.8f,
            color = Color(0xFFB71C1C)
        ),
        VoiceEffect(
            type = VoiceEffectType.GIANT,
            nameRes = 0,
            iconEmoji = "🧌",
            pitchShift = 0.4f,
            speedShift = 0.7f,
            color = Color(0xFF33691E)
        )
    )

    fun getDisplayName(type: VoiceEffectType): String = when (type) {
        VoiceEffectType.NORMAL  -> "Normal"
        VoiceEffectType.GIRL    -> "Girl"
        VoiceEffectType.MAN     -> "Man"
        VoiceEffectType.WOMAN   -> "Woman"
        VoiceEffectType.CHILD   -> "Child"
        VoiceEffectType.OLD_MAN -> "Old Man"
        VoiceEffectType.ROBOT   -> "Robot"
        VoiceEffectType.ALIEN   -> "Alien"
        VoiceEffectType.DEVIL   -> "Devil"
        VoiceEffectType.GIANT   -> "Giant"
    }
}
