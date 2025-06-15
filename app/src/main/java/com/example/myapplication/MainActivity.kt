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
import androidx.compose.material.icons.outlined.Home // Import outlined icon
import androidx.compose.material.icons.outlined.Info // Import outlined icon
import androidx.compose.material.icons.outlined.Settings // Import outlined icon
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
import com.example.myapplication.navigation.Screen // Adjusted import
import com.example.myapplication.screens.HomeScreenContent
import com.example.myapplication.screens.Item1ScreenContent
import com.example.myapplication.screens.Item2ScreenContent
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.shareContent // Adjusted import
import kotlinx.coroutines.launch

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

                if (showInfoDialog) {
                    InfoDialog(onDismissRequest = { showInfoDialog = false })
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                        ) {
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
                                selected = currentScreen == Screen.Home, // Keep for accessibility and semantics
                                onClick = {
                                    currentScreen = Screen.Home
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), // Example: Light primary background
                                    unselectedContainerColor = Color.Transparent, // No background when not selected
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )

                            // Item 1
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        if (currentScreen == Screen.Item1Screen) Icons.Filled.Info else Icons.Outlined.Info,
                                        contentDescription = "Item 1"
                                    )
                                },
                                label = {
                                    Text(
                                        "Item 1",
                                        fontWeight = if (currentScreen == Screen.Item1Screen) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                selected = currentScreen == Screen.Item1Screen,
                                onClick = {
                                    currentScreen = Screen.Item1Screen
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    unselectedContainerColor = Color.Transparent,
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )

                            // Item 2
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        if (currentScreen == Screen.Item2Screen) Icons.Filled.Settings else Icons.Outlined.Settings,
                                        contentDescription = "Item 2"
                                    )
                                },
                                label = {
                                    Text(
                                        "Item 2",
                                        fontWeight = if (currentScreen == Screen.Item2Screen) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                selected = currentScreen == Screen.Item2Screen,
                                onClick = {
                                    currentScreen = Screen.Item2Screen
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
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
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        when (currentScreen) {
                                            Screen.Home -> "My App - Home"
                                            Screen.Item1Screen -> "My App - Item 1"
                                            Screen.Item2Screen -> "My App - Item 2"
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
                                            Screen.Item1Screen -> "Exploring Item 1 in My App!"
                                            Screen.Item2Screen -> "Looking at Item 2 in My App!"
                                        }
                                        shareContent(
                                            context,
                                            "From My App",
                                            shareText
                                        )
                                    }) {
                                        Icon(Icons.Filled.Share, "Share current screen")
                                    }
                                    IconButton(onClick = { /* TODO: Handle Settings action */ }) {
                                        Icon(Icons.Filled.Settings, "Settings")
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
                                Screen.Item1Screen -> Item1ScreenContent()
                                Screen.Item2Screen -> Item2ScreenContent()
                            }
                        }
                    }
                }
            }
        }
    }
}