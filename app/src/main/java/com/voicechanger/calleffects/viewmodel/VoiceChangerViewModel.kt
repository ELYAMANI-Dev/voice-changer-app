package com.voicechanger.calleffects.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.voicechanger.calleffects.data.model.VoiceEffect
import com.voicechanger.calleffects.data.model.VoiceEffectType
import com.voicechanger.calleffects.data.model.VoiceEffects
import com.voicechanger.calleffects.services.AudioService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class VoiceChangerUiState(
    val selectedEffect: VoiceEffect = VoiceEffects.all.first(),
    val isRecording: Boolean = false,
    val isPlaying: Boolean = false,
    val hasRecording: Boolean = false,
    val permissionGranted: Boolean = false
)

class VoiceChangerViewModel(application: Application) : AndroidViewModel(application) {

    private val audioService = AudioService(application)

    private val _uiState = MutableStateFlow(VoiceChangerUiState())
    val uiState: StateFlow<VoiceChangerUiState> = _uiState.asStateFlow()

    fun onPermissionResult(granted: Boolean) {
        _uiState.value = _uiState.value.copy(permissionGranted = granted)
    }

    fun selectEffect(effect: VoiceEffect) {
        _uiState.value = _uiState.value.copy(selectedEffect = effect)
        if (audioService.isPlaying) stopPlayback()
    }

    fun toggleRecording() {
        if (_uiState.value.isRecording) {
            stopRecording()
        } else {
            startRecording()
        }
    }

    private fun startRecording() {
        if (audioService.startRecording()) {
            _uiState.value = _uiState.value.copy(isRecording = true, hasRecording = false)
        }
    }

    private fun stopRecording() {
        val success = audioService.stopRecording()
        _uiState.value = _uiState.value.copy(isRecording = false, hasRecording = success)
    }

    fun playWithCurrentEffect() {
        if (!audioService.hasRecording()) return
        val effect = _uiState.value.selectedEffect
        _uiState.value = _uiState.value.copy(isPlaying = true)
        audioService.playWithEffect(effect.pitchShift, effect.speedShift) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isPlaying = false)
            }
        }
    }

    fun stopPlayback() {
        audioService.stopPlayback()
        _uiState.value = _uiState.value.copy(isPlaying = false)
    }

    override fun onCleared() {
        super.onCleared()
        audioService.release()
    }
}
