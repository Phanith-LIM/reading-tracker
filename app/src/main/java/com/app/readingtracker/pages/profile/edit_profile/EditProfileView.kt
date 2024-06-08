package com.app.readingtracker.pages.profile.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.app.readingtracker.core.DataStoreManager
import com.app.readingtracker.core.UiState
import com.app.readingtracker.share.composable.ErrorComposable
import com.app.readingtracker.share.composable.LoadingComposable
import com.app.readingtracker.share.sealed.ImageData
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kSpace
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class EditProfileView : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var imageData by remember { mutableStateOf<ImageData>(ImageData.ImageUrl("https://images.ctfassets.net/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg?w=1200&h=992&q=70&fm=webp")) }
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            it?.let { uri ->
                imageData = ImageData.ImageUri(uri)
            }
        }

        val viewModel = viewModel<EditProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current
        val profileData by viewModel.profileData.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        val focusManager = LocalFocusManager.current

        LaunchedEffect(Unit) {
            val token = DataStoreManager.read(context,"refresh").firstOrNull()
            if(token != null) {
                viewModel.getProfile(token)
            }
        }

        LaunchedEffect(profileData) {
            profileData?.avatar?.let { avatarUrl ->
                imageData = ImageData.ImageUrl(avatarUrl)
            }
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    title = { Text(text = "Edit Profile") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigator.pop() },
                            content = { Icon(Icons.Default.ArrowBackIosNew, contentDescription = null) }
                        )
                    }
                )
            },
            content = { paddingValues ->
                when(uiState) {
                    UiState.LOADING -> { LoadingComposable(it = paddingValues) }
                    UiState.ERROR -> { ErrorComposable(it = paddingValues) }
                    UiState.SUCCESS -> {
                        var text by remember { mutableStateOf(profileData?.name ?: "") }
                        // Remove this line to prevent resetting imageData
                        // imageData = ImageData.ImageUrl(profileData?.avatar ?: "https://images.ctfassets.net/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg?w=1200&h=992&q=70&fm=webp")
                        Column (
                            modifier = Modifier
                                .padding(paddingValues)
                                .padding(horizontal = kPadding * 2)
                                .pointerInput(Unit) {
                                    detectTapGestures(onTap = {
                                        focusManager.clearFocus()
                                    })
                                },
                            verticalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f), // Occupy remaining space
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    content = {
                                        CircleImageWithIconAndFilePicker(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .size(150.dp),
                                            imageData = imageData,
                                            onFilePick = {
                                                launcher.launch(
                                                    PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                )
                                            },
                                        )
                                        MyTextField(
                                            text = text,
                                            onTextChanged = {
                                                text = it
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(kSpace))
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .fillMaxHeight(0.113f),
                                            value = profileData?.email ?: "NAN",
                                            onValueChange = {  },
                                            label = { Text("Email") },
                                            maxLines = 1,
                                            readOnly = true,
                                            shape = MaterialTheme.shapes.medium,
                                            singleLine = true
                                        )
                                    }
                                )
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min),
                                    shape = MaterialTheme.shapes.medium,
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    content = { Text(text = "Update") },
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.updateProfile(text, imageData, context)
                                        }
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
