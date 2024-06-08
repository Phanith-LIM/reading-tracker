package com.app.readingtracker.pages.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.pages.profile.edit_profile.EditProfileView
import com.app.readingtracker.pages.profile.policy.PrivacyPolicyView
import com.app.readingtracker.pages.splashscreen.SplashScreen
import com.app.readingtracker.share.composable.ErrorComposable
import com.app.readingtracker.share.composable.LoadingComposable
import com.app.readingtracker.ui.theme.kPadding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

data class ProfileView(val navigator: Navigator?): Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = viewModel<ProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current
        val profileData by viewModel.profileData.collectAsState()
        val coroutineScope = rememberCoroutineScope()

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
                )
            },
            content = {
                when(uiState) {
                    UiState.LOADING -> { LoadingComposable(it = it) }
                    UiState.ERROR -> { ErrorComposable(it = it) }
                    UiState.SUCCESS -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = kPadding * 2)
                                .padding(it),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            content = {
                                CircleAvatar(name = profileData?.name, imageUrl = profileData?.avatar, email = profileData?.email)
                                UserDataReadingComposable(profileData?.books ?: 0, profileData?.read ?: 0)
                                Column (
                                    modifier = Modifier.clip(MaterialTheme.shapes.large).background(Color.Black.copy(0.07f)).padding(kPadding),
                                    content = {
                                        ListItem(
                                            modifier = Modifier.fillMaxHeight(0.10f).clickable {
                                                navigator?.push(EditProfileView())
                                            },
                                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                            headlineContent = { Text(text = "Edit Profile") },
                                            leadingContent = { Icon(Icons.Default.Edit, contentDescription = null) },
                                            trailingContent = { Icon(Icons.AutoMirrored.Default.ArrowForwardIos, contentDescription = null) }
                                        )
                                        ListItem(
                                            modifier = Modifier.fillMaxHeight(0.10f).clickable {
                                                navigator?.push(PrivacyPolicyView())
                                            },
                                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                            headlineContent = { Text(text = "Privacy Policy") },
                                            leadingContent = { Icon(Icons.Default.Policy, contentDescription = null) },
                                            trailingContent = { Icon(Icons.AutoMirrored.Default.ArrowForwardIos, contentDescription = null) }
                                        )
                                        ListItem(
                                            modifier = Modifier.fillMaxHeight(0.10f).clickable {
                                                coroutineScope.launch {
                                                    FirebaseAuth.getInstance().signOut()
                                                    DataStoreManager.clearData(context)
                                                    navigator?.replaceAll(SplashScreen())
                                                }
                                            },
                                            colors = ListItemDefaults.colors( containerColor = Color.Transparent),
                                            headlineContent = { Text(text = "Sign Out", color = Color.Red) },
                                            leadingContent = { Icon(Icons.AutoMirrored.Default.Logout, contentDescription = null, tint = Color.Red) },
                                            trailingContent = { Icon(Icons.AutoMirrored.Default.ArrowForwardIos, contentDescription = null) }
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}