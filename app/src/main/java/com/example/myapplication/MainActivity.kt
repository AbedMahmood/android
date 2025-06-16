package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.myapplication.composeables.DrawerHeader
import com.example.myapplication.composeables.InfoDialog
import com.example.myapplication.navigation.Screen
import com.example.myapplication.screens.HomeScreenContent
import com.example.myapplication.screens.SettingScreenContent
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.shareContent
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.RectangleShape

import androidx.activity.compose.BackHandler // Import BackHandler

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                var currentScreen by remember { mutableStateOf(Screen.Home) }
                var showInfoDialog by remember { mutableStateOf(false) }
                val context = LocalContext.current

                // Handle back press
                BackHandler(enabled = drawerState.isOpen) {
                    scope.launch {
                        drawerState.close()
                    }
                }

                BackHandler(enabled = showInfoDialog) {
                    showInfoDialog = false
                }

                // Default back handler for screens
                // This will only be active if the drawer is closed and the info dialog is not shown
                BackHandler(enabled = currentScreen != Screen.Home && !drawerState.isOpen && !showInfoDialog) {
                    currentScreen = Screen.Home // Navigate to Home screen
                }


                if (showInfoDialog) {
                    InfoDialog(onDismissRequest = { showInfoDialog = false })
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                        ) {
                            // Use a Column to arrange items vertically
                            Column(modifier = Modifier.fillMaxSize()) {
                                DrawerHeader()
                                HorizontalDivider()

                                // Home Item
                                NavigationDrawerItem(
                                    icon = {
                                        Icon(
                                            if (currentScreen == Screen.Home) Icons.Filled.Home else Icons.Outlined.Home,
                                            contentDescription = "Home"
                                        )
                                    },
                                    label = {
                                        Text(
                                            "Home",
                                            fontWeight = if (currentScreen == Screen.Home) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    selected = currentScreen == Screen.Home,
                                    onClick = {
                                        currentScreen = Screen.Home
                                        scope.launch { drawerState.close() }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RectangleShape,
                                    colors = NavigationDrawerItemDefaults.colors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        unselectedContainerColor = Color.Transparent,
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                HorizontalDivider()

                                // Settings Item
                                NavigationDrawerItem(
                                    icon = {
                                        Icon(
                                            if (currentScreen == Screen.SettingScreen) Icons.Filled.Settings else Icons.Outlined.Settings,
                                            contentDescription = "Settings"
                                        )
                                    },
                                    label = {
                                        Text(
                                            "Settings",
                                            fontWeight = if (currentScreen == Screen.SettingScreen) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    selected = currentScreen == Screen.SettingScreen,
                                    onClick = {
                                        currentScreen = Screen.SettingScreen
                                        scope.launch { drawerState.close() }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RectangleShape,
                                    colors = NavigationDrawerItemDefaults.colors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        unselectedContainerColor = Color.Transparent,
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        when (currentScreen) {
                                            Screen.Home -> "My App - Home"
                                            Screen.SettingScreen -> "My App - Settings"
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
                                            Screen.SettingScreen -> "Settings screen of My App!"
                                        }
                                        shareContent(
                                            context,
                                            "From My App",
                                            shareText
                                        )
                                    }) {
                                        Icon(Icons.Filled.Share, "Share current screen")
                                    }
                                    IconButton(onClick = {
                                        showInfoDialog = true
                                    }) {
                                        Icon(Icons.Filled.Info, "About")
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            when (currentScreen) {
                                Screen.Home -> HomeScreenContent()
                                Screen.SettingScreen -> SettingScreenContent()
                            }
                        }
                    }
                }
            }
        }
    }
}