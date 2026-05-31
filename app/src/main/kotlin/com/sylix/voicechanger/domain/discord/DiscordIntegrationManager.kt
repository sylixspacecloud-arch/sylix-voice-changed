package com.sylix.voicechanger.domain.discord

import android.content.Context
import android.content.pm.PackageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

interface DiscordIntegrationManager {
    val discordState: StateFlow<DiscordState>
    fun checkDiscordInstalled(): Boolean
    fun checkDiscordRunning(): Boolean
    fun setupDiscordIntegration(): Boolean
}

data class DiscordState(
    val isInstalled: Boolean = false,
    val isRunning: Boolean = false,
    val isSetupComplete: Boolean = false,
    val error: String? = null
)

class DiscordIntegrationManagerImpl(private val context: Context) : DiscordIntegrationManager {
    private val _discordState = MutableStateFlow(DiscordState())
    override val discordState: StateFlow<DiscordState> = _discordState

    companion object {
        private const val DISCORD_PACKAGE = "com.discord"
    }

    override fun checkDiscordInstalled(): Boolean {
        return try {
            context.packageManager.getApplicationInfo(DISCORD_PACKAGE, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.d("Discord app not installed")
            false
        }
    }

    override fun checkDiscordRunning(): Boolean {
        return try {
            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            val runningApps = activityManager.runningAppProcesses ?: return false
            runningApps.any { it.processName == DISCORD_PACKAGE }
        } catch (e: Exception) {
            Timber.e(e, "Error checking if Discord is running")
            false
        }
    }

    override fun setupDiscordIntegration(): Boolean {
        return try {
            if (!checkDiscordInstalled()) {
                _discordState.value = _discordState.value.copy(
                    error = "Discord is not installed"
                )
                return false
            }

            // In a real implementation, this would configure audio routing
            // For now, we just mark setup as complete
            _discordState.value = _discordState.value.copy(
                isInstalled = true,
                isSetupComplete = true,
                error = null
            )
            true
        } catch (e: Exception) {
            Timber.e(e, "Error setting up Discord integration")
            _discordState.value = _discordState.value.copy(
                error = "Setup failed: ${e.message}"
            )
            false
        }
    }
}
