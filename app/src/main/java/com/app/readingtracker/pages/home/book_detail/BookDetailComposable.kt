package com.app.readingtracker.pages.home.book_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

@Composable
fun BookCover(url: String) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    return Box(
        modifier = Modifier.fillMaxWidth().height((screenHeight * 0.3f).coerceAtMost(screenHeight)).background(Color.Red),
        content = {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = url,
                loading = {
                    Box(
                        modifier = Modifier.width(32.dp).height(32.dp).align(Alignment.Center),
                        content = {
                            CircularProgressIndicator()
                        }
                    )
                },
                alpha = 0.8f,
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.Low,
                clipToBounds = true,
                contentDescription = null
            )
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize().size(256.dp).align(Alignment.Center),
                model = url,
                loading = {
                    Box(
                        modifier = Modifier.width(32.dp).height(32.dp),
                        content = {
                            CircularProgressIndicator()
                        }
                    )
                },
                contentScale = ContentScale.FillHeight,
                filterQuality = FilterQuality.Low,
                clipToBounds = true,
                contentDescription = null
            )
        }
    )
}

@Composable
fun DialogCollection(shouldShowDialog: MutableState<Boolean>, selectedShelve: MutableState<Shelve?>, onSave: () -> Unit) {
    if (shouldShowDialog.value) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = {
                shouldShowDialog.value = false
                selectedShelve.value = null
            },
            title = {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = {
                        Text(text = "Choose Shelve")
                        IconButton(
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                )
                            },
                            onClick = {
                                shouldShowDialog.value = false
                                selectedShelve.value = null
                            }
                        )
                    }
                )
            },
            text = {
                Column (
                    content = {
                        ListItem(
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.background
                            ),
                            headlineContent = {
                                Text("Want to read", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400, fontSize = 18.sp))
                            },
                            trailingContent = {
                                RadioButton(
                                    selected = selectedShelve.value == Shelve.WANT,
                                    onClick = { selectedShelve.value = Shelve.WANT },
                                )
                            }
                        )
                        ListItem(
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.background
                            ),
                            headlineContent = {
                                Text("Currently Read", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400, fontSize = 18.sp))
                            },
                            trailingContent = {
                                RadioButton(
                                    selected = selectedShelve.value == Shelve.CURRENT,
                                    onClick = { selectedShelve.value = Shelve.CURRENT },
                                )
                            }
                        )
                        ListItem(
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.background
                            ),
                            headlineContent = {
                                Text("Reading", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400, fontSize = 18.sp))
                            },
                            trailingContent = {
                                RadioButton(
                                    selected = selectedShelve.value == Shelve.READ,
                                    onClick = { selectedShelve.value = Shelve.READ },
                                )
                            }
                        )
                    }
                )
            },
            confirmButton = { // 6
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                        onSave()
                        selectedShelve.value = null
                    },
                    content = {
                        Text(
                            text = "Save",
                            color = Color.White
                        )
                    }
                )
            }
        )
    }
}