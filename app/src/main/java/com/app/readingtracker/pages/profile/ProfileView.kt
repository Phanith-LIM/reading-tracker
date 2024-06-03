package com.app.readingtracker.pages.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.ui.theme.kPadding
import kotlinx.coroutines.flow.firstOrNull

class ProfileView: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = viewModel<ProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current
        val profileData by viewModel.profileData.collectAsState()

        LaunchedEffect(Unit) {
            val token = DataStoreManager.read(context,"refresh").firstOrNull()
            if(token != null) {
                viewModel.getProfile(token)
            }
        }
        return Scaffold (
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                    title = { Text("Profile") },
                    actions = {
                        IconButton(
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Color.Gray.copy(alpha = 0.2f),
                            ),
                            onClick = {

                            },
                            content = { Icon(Icons.Default.Settings, contentDescription = null) }
                        )
                    }
                )
            },
            content = {
                when(uiState) {
                    UiState.LOADING -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            contentAlignment = Alignment.Center,
                            content = { CircularProgressIndicator() }
                        )
                    }

                    UiState.ERROR -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center,
                            content = { Text("An error occurred. Please try again. ${viewModel.errorMessage.value}") }
                        )
                    }

                    UiState.SUCCESS -> {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(it),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            content = {
                                Spacer(modifier = Modifier.height(16.dp))
                                CircleAvatar(name = profileData?.name ?: "NAN", imageUrl = profileData?.avatar ?: "NAN")
                                Spacer(modifier = Modifier.height(8.dp))
                                UserDataReadingComposable(profileData?.books ?: 0, profileData?.read ?: 0)
                            }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun UserDataReadingComposable(books: Int, read: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.15f).padding(horizontal = kPadding),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = books.toString(),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(text = "Books", style = MaterialTheme.typography.titleLarge)
                }
            )
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                color = Color.Gray
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = read.toString(),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(text = "Read", style = MaterialTheme.typography.titleLarge)
                }
            )
        }
    )
}
