package com.app.readingtracker.pages.profile.policy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.app.readingtracker.core.markdownUrl
import com.mukesh.MarkDown
import java.net.URL

class PrivacyPolicyView: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        return Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    title = { Text(text = "Privacy Policy") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigator.pop() },
                            content = { Icon(Icons.Default.ArrowBackIosNew, contentDescription = null) }
                        )
                    }
                )
            },
            content = {
                MarkDown(
                    url = URL(markdownUrl),
                    modifier = Modifier.fillMaxSize().padding(it)
                )
            }
        )
    }
}