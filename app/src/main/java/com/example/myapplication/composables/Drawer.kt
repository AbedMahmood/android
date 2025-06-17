package com.example.myapplication.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.navigation.Screen // Ensure this import path is correct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    currentScreen: Screen,
    onScreenChange: (Screen) -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    // Use a Column to arrange items vertically
    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp) // Example height
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart // Or any alignment you prefer
        ) {
            Text(
                text = "My App Drawer",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

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
                onScreenChange(Screen.Home)
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

        // Sample Item
        NavigationDrawerItem(
            icon = {
                Icon(
                    if (currentScreen == Screen.Sample) Icons.Filled.Info else Icons.Outlined.Info,
                    contentDescription = "Sample"
                )
            },
            label = {
                Text(
                    "Sample",
                    fontWeight = if (currentScreen == Screen.Sample) FontWeight.Bold else FontWeight.Normal
                )
            },
            selected = currentScreen == Screen.Sample,
            onClick = {
                onScreenChange(Screen.Sample)
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

        // Add other menu items here if needed, for example:
        // NavigationDrawerItem(...)

        Spacer(modifier = Modifier.weight(1f)) // Pushes items below to the bottom

        HorizontalDivider()

        // Settings Item
        NavigationDrawerItem(
            icon = {
                Icon(
                    if (currentScreen == Screen.Settings) Icons.Filled.Settings else Icons.Outlined.Settings,
                    contentDescription = "Settings"
                )
            },
            label = {
                Text(
                    "Settings",
                    fontWeight = if (currentScreen == Screen.Settings) FontWeight.Bold else FontWeight.Normal
                )
            },
            selected = currentScreen == Screen.Settings,
            onClick = {
                onScreenChange(Screen.Settings)
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