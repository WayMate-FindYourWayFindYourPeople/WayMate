package com.waymate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.waymate.app.ui.auth.WayMateAuthNavHost
import com.waymate.app.ui.theme.WayMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WayMateTheme {
                WayMateAuthNavHost()
            }
        }
    }
}