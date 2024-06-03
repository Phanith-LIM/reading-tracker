package com.app.readingtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import com.app.readingtracker.pages.splashscreen.SplashScreen
import com.app.readingtracker.ui.theme.ReadingtrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        this.applicationContext
        setContent {
            ReadingtrackerTheme {
                Navigator( screen = SplashScreen())
            }
        }
    }
}

