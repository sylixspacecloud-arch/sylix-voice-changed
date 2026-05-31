package com.sylix.voicechanger.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sylix.voicechanger.ui.screens.HomeScreen
import com.sylix.voicechanger.ui.screens.SettingsScreen
import com.sylix.voicechanger.ui.screens.DiagnosticsScreen

@Composable
fun AppNavigation() {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        NavigationItem("Home", Icons.Default.Home, 0),
        NavigationItem("Voice Library", Icons.Default.Home, 1),
        NavigationItem("Live Test", Icons.Default.Home, 2),
        NavigationItem("Discord", Icons.Default.Home, 3),
        NavigationItem("Downloads", Icons.Default.Home, 4),
        NavigationItem("Settings", Icons.Default.Settings, 5),
        NavigationItem("Diagnostics", Icons.Default.Info, 6)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> HomeScreen(modifier = Modifier.padding(innerPadding))
            1 -> VoiceLibraryScreen(modifier = Modifier.padding(innerPadding))
            2 -> LiveTestScreen(modifier = Modifier.padding(innerPadding))
            3 -> DiscordIntegrationScreen(modifier = Modifier.padding(innerPadding))
            4 -> DownloadsScreen(modifier = Modifier.padding(innerPadding))
            5 -> SettingsScreen(modifier = Modifier.padding(innerPadding))
            6 -> DiagnosticsScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

data class NavigationItem(
    val label: String,
    val icon: androidx.compose.material.icons.materialIcon,
    val index: Int
)
