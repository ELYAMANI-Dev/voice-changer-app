package com.voicechanger.calleffects.services

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.PlaybackParams
import android.os.Build
import java.io.File

class AudioService(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var outputFile: File? = null

    val isRecording: Boolean get() = mediaRecorder != null
    val isPlaying: Boolean get() = mediaPlayer?.isPlaying == true

    fun startRecording(): Boolean {
        return try {
            val file = File(context.cacheDir, "voice_record.3gp").also { outputFile = it }
            mediaRecorder = (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                MediaRecorder(context) else @Suppress("DEPRECATION") MediaRecorder()
            ).apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            releaseRecorder()
            false
        }
    }

    fun stopRecording(): Boolean {
        return try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            outputFile != null
        } catch (e: Exception) {
            e.printStackTrace()
            releaseRecorder()
            false
        }
    }

    fun playWithEffect(pitchShift: Float, speedShift: Float, onComplete: () -> Unit) {
        val file = outputFile ?: return
        if (!file.exists()) return

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(file.absolutePath)
            prepare()
            playbackParams = PlaybackParams().apply {
                pitch = pitchShift.coerceIn(0.5f, 2.0f)
                speed = speedShift.coerceIn(0.5f, 2.0f)
            }
            setOnCompletionListener {
                onComplete()
                it.release()
                mediaPlayer = null
            }
            start()
        }
    }

    fun stopPlayback() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        mediaPlayer = null
    }

    fun hasRecording(): Boolean = outputFile?.exists() == true

    fun release() {
        releaseRecorder()
        stopPlayback()
    }

    private fun releaseRecorder() {
        try {
            mediaRecorder?.release()
        } catch (_: Exception) {}
        mediaRecorder = null
    }
}
