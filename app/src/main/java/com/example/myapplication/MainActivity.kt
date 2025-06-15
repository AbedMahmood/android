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
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.composables.DrawerHeader
import com.example.myapplication.composables.InfoDialog
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

                            NavigationDrawerItem(
                                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                                label = { Text("Home") },
                                selected = currentScreen == Screen.Home,
                                onClick = {
                                    currentScreen = Screen.Home
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Filled.Info, contentDescription = "Item 1") },
                                label = { Text("Item 1") },
                                selected = currentScreen == Screen.Item1Screen,
                                onClick = {
                                    currentScreen = Screen.Item1Screen
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Filled.Settings, contentDescription = "Item 2") },
                                label = { Text("Item 2") },
                                selected = currentScreen == Screen.Item2Screen,
                                onClick = {
                                    currentScreen = Screen.Item2Screen
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
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