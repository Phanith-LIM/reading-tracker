package com.app.readingtracker.pages.splashscreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.readingtracker.R


@Composable
fun SplashScreenView() {
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background,
        content = { it ->
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo app",
                    modifier = Modifier.width(150.dp).height(150.dp)
                )
            }
        }
    )
}

