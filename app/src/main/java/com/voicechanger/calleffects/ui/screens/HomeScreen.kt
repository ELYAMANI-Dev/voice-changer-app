package com.voicechanger.calleffects.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voicechanger.calleffects.data.model.VoiceEffect
import com.voicechanger.calleffects.data.model.VoiceEffects
import com.voicechanger.calleffects.ui.theme.BrandPrimary
import com.voicechanger.calleffects.ui.theme.BrandSurface
import com.voicechanger.calleffects.ui.theme.BrandSurfaceAlt
import com.voicechanger.calleffects.viewmodel.VoiceChangerUiState
import com.voicechanger.calleffects.viewmodel.VoiceChangerViewModel

@Composable
fun HomeScreen(viewModel: VoiceChangerViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BrandSurface)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "🎙️ Voice Changer",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Voice effect grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(VoiceEffects.all) { effect ->
                VoiceEffectCard(
                    effect = effect,
                    isSelected = uiState.selectedEffect.type == effect.type,
                    onClick = { viewModel.selectEffect(effect) }
                )
            }
        }

        // Controls
        RecordingControls(
            uiState = uiState,
            onToggleRecord = viewModel::toggleRecording,
            onPlay = viewModel::playWithCurrentEffect,
            onStop = viewModel::stopPlayback
        )
    }
}

@Composable
private fun VoiceEffectCard(
    effect: VoiceEffect,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) BrandPrimary else Color.Transparent

    Column(
        modifier = Modifier
            .aspectRatio(0.9f)
            .clip(RoundedCornerShape(16.dp))
            .background(BrandSurfaceAlt)
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(effect.color.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = effect.iconEmoji,
                fontSize = 28.sp
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = VoiceEffects.getDisplayName(effect.type),
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) BrandPrimary else MaterialTheme.colorScheme.onSurface,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RecordingControls(
    uiState: VoiceChangerUiState,
    onToggleRecord: () -> Unit,
    onPlay: () -> Unit,
    onStop: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BrandSurface)
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Record button
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FloatingActionButton(
                onClick = onToggleRecord,
                containerColor = if (uiState.isRecording) Color(0xFFD32F2F) else BrandPrimary,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = if (uiState.isRecording) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = if (uiState.isRecording) "Stop" else "Record",
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (uiState.isRecording) "Recording…" else "Record",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Play / Stop button
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FloatingActionButton(
                onClick = if (uiState.isPlaying) onStop else onPlay,
                containerColor = if (uiState.hasRecording) Color(0xFF2E7D32) else Color(0xFF333333),
                modifier = Modifier.size(64.dp),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    imageVector = if (uiState.isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                    contentDescription = if (uiState.isPlaying) "Stop playback" else "Play",
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (uiState.isPlaying) "Playing…" else "Play",
                style = MaterialTheme.typography.labelSmall,
                color = if (uiState.hasRecording) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
