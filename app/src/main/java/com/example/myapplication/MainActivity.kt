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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.myapplication.composables.AppScaffold
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
                val backStack = remember { mutableStateListOf(Screen.Home) }
                val currentScreen = backStack.last()
                var showInfoDialog by remember { mutableStateOf(false) }

                // Navigation function: push screen on stack if not already on top
                fun navigateTo(screen: Screen) {
                    if (backStack.last() != screen) {
                        backStack.add(screen)
                    }
                }

                // Back function: pop from stack if more than one screen
                fun onBack() {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.lastIndex)
                    }
                }

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

                // Handle back press for navigation stack
                BackHandler(enabled = backStack.size > 1 && !drawerState.isOpen && !showInfoDialog) {
                    onBack()
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
                                    // Only navigate if not already on this screen
                                    navigateTo(newScreen)
                                    // Optionally close the drawer here:
                                    scope.launch { drawerState.close() }
                                },
                                drawerState = drawerState,
                                scope = scope
                            )
                        }
                    }
                ) {
                    AppScaffold(
                        drawerState = drawerState,
                        currentScreen = currentScreen,
                        onShowInfoDialog = { showInfoDialog = true },
                        //onNavigate = { screen -> navigateTo(screen) } // Pass navigation callback if needed
                    )
                }
            }
        }
    }
}
