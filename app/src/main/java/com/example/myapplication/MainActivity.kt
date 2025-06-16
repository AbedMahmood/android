package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.myapplication.composables.MainAppScaffold
import com.example.myapplication.composables.Drawer
import com.example.myapplication.composables.InfoDialog
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.theme.MyApplicationTheme
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

                // Handle back press for drawer
                BackHandler(enabled = drawerState.isOpen) {
                    scope.launch {
                        drawerState.close()
                    }
                }

                // Handle back press for info dialog
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
                            Drawer(
                                currentScreen = currentScreen,
                                onScreenChange = { newScreen ->
                                    currentScreen = newScreen
                                    // The drawer closing is handled within DrawerMenuItems
                                },
                                drawerState = drawerState,
                                scope = scope
                            )
                        }
                    }
                ) {
                    // Use the new MainAppScaffold composable
                    MainAppScaffold(
                        drawerState = drawerState,
                        currentScreen = currentScreen,
                        onShowInfoDialog = { showInfoDialog = true }
                    )
                }
            }
        }
    }
}