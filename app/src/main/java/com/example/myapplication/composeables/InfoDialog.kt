package com.example.myapplication.composeables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun InfoDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "About My App")
        },
        text = {
            Text("This is a sample application demonstrating Jetpack Compose features including a navigation drawer and basic screen navigation.")
        },
        confirmButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text("OK")
            }
        }
    )
}