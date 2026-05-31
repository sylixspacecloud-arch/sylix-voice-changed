package com.sylix.voicechanger.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import timber.log.Timber

class VoiceProcessingService : Service() {
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "voice_processing"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("VoiceProcessingService started")

        // Create foreground notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Sylix Voice Changer")
            .setContentText("Processing voice...")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("VoiceProcessingService destroyed")
        stopForeground(STOP_FOREGROUND_REMOVE)
    }
}
