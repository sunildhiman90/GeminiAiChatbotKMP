package com.codingambitions.geminiaichatbotkmp

import App
import AppContent
import ByteArrayFactory
import HomeViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppContent(
                viewModel = HomeViewModel(),
                byteArrayFactory = ByteArrayFactory(baseContext)
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    //App()
}