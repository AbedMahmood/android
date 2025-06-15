package com.example.myapplication.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) { // Added modifier parameter
    var borderColor by remember { mutableStateOf(Color.Green) } // Changed initial color for differentiation

    // Function to generate a random color
    fun randomColor(): Color {
        val red = Random.nextInt(256)
        val green = Random.nextInt(256)
        val blue = Random.nextInt(256)
        return Color(red, green, blue)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .border(2.dp, borderColor)
                .clickable {
                    borderColor = randomColor() // Change border color on click
                }
                .padding(16.dp)
        ) {
            Text(text = "Welcome to the Home Screen!") // Changed the text
        }
    }
}