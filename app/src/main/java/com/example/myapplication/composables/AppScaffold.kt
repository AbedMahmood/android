package com.example.myapplication.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.navigation.Screen
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.SampleScreen
import com.example.myapplication.screens.SettingsScreen
import com.example.myapplication.utils.shareContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    drawerState: DrawerState,
    currentScreen: Screen,
    onShowInfoDialog: () -> Unit // Callback to show the info dialog
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (currentScreen) {
                            Screen.Home -> "My App - Home"
                            Screen.Settings -> "My App - Settings"
                            Screen.Sample -> "My App - Sample"
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Navigation menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val shareText = when (currentScreen) {
                            Screen.Home -> "Checking out the Home screen of My App!"
                            Screen.Settings -> "Settings screen of My App!"
                            Screen.Sample -> "Sample screen of My App!"
                        }
                        shareContent(
                            context,
                            "From My App",
                            shareText
                        )
                    }) {
                        Icon(Icons.Filled.Share, "Share current screen")
                    }
                    IconButton(onClick = onShowInfoDialog) { // Use the callback
                        Icon(Icons.Filled.Info, "About")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                Screen.Home -> HomeScreen()
                Screen.Settings -> SettingsScreen()
                Screen.Sample -> SampleScreen()
                // Add more screens here if needed
            }
        }
    }
}