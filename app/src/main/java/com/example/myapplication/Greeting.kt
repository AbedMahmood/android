package com.example.myapplication

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var borderColor by remember { mutableStateOf(Color.Blue) }

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
                    borderColor = randomColor()
                }
                .padding(16.dp)
        ) {
            Text(text = "Hello $name!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting(name = "Android")
}