package com.app.readingtracker.pages.home.book_detail
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.app.readingtracker.R
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kPrimary
import com.app.readingtracker.ui.theme.kSpace



data class BookDetailView(private val id: String): Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val openDialog = remember { mutableStateOf(false) }
        val selectedShelve = remember { mutableStateOf<Shelve?>(null) }
        DialogCollection(shouldShowDialog = openDialog, selectedShelve = selectedShelve)
        val viewModel = viewModel<BookDetailViewModel>(factory = BookDetailViewModelFactory(id))
        return Scaffold(
           modifier = Modifier.background(Color.White),
           topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                    title = { Text(text = "Book") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigator.pop() },
                            content = { Icon(Icons.Default.ArrowBackIosNew, contentDescription = null) }
                        )
                    }
                )
           },
           content = { it ->
               if (viewModel.isLoading.value) {
                   Box(
                       modifier = Modifier.fillMaxSize().padding(it),
                       contentAlignment = Alignment.Center,
                       content = { CircularProgressIndicator() }
                   )
               } else {
                   Column(
                       modifier = Modifier
                           .fillMaxSize()
                           .padding(it),
                       verticalArrangement = Arrangement.SpaceBetween,
                       content = {
                           Column(
                               verticalArrangement = Arrangement.Top,
                               horizontalAlignment = Alignment.CenterHorizontally,
                               content = {
                                   Spacer(modifier = Modifier.height(kSpace))
                                   BookCover(url = viewModel.dataBooks.collectAsState().value?.thumbnail ?: "https://marketplace.canva.com/EAFaQMYuZbo/1/0/1003w/canva-brown-rusty-mystery-novel-book-cover-hG1QhA7BiBU.jpg")
                                   Spacer(modifier = Modifier.height(kSpace * 2))
                                   Text(
                                       text = viewModel.dataBooks.collectAsState().value?.title ?: "NAN",
                                       textAlign = TextAlign.Center,
                                       fontWeight = FontWeight.W500,
                                       fontSize = 18.sp,
                                   )
                                   Spacer(modifier = Modifier.height(kSpace / 2))
                                   Text(
                                       buildAnnotatedString {
                                           append("by")
                                           append(" ")
                                           withStyle(style = SpanStyle(color = kPrimary, fontWeight = FontWeight.W500,)) {
                                               append(viewModel.dataBooks.value?.authors ?: "NAN")
                                           }
                                       }
                                   )
                                   Spacer(modifier = Modifier.height(kSpace * 3))
                                   Box(
                                       modifier = Modifier.padding(kSpace)
                                   ) {
                                       Column {
                                           ListItem(
                                               colors = ListItemDefaults.colors(
                                                   containerColor = MaterialTheme.colorScheme.background
                                               ),
                                               modifier = Modifier
                                                   .border(
                                                       width = 1.5.dp,
                                                       color = Color.Gray.copy(alpha = 0.2f),
                                                       shape = MaterialTheme.shapes.medium
                                                   )
                                                   .padding(kSpace),
                                               headlineContent = {
                                                   Text("ISBN-13", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400, fontSize = 16.sp))
                                               },
                                               supportingContent = {
                                                   Text(viewModel.dataBooks.collectAsState().value?.isbn13.toString())
                                               },
                                               leadingContent = {
                                                   Icon(
                                                       painter = painterResource(R.drawable.barcode),
                                                       contentDescription = null,
                                                       modifier = Modifier.size(32.dp),
                                                       tint = if(isSystemInDarkTheme()) Color.White else Color.Black
                                                   )
                                               }
                                           )
                                           Spacer(modifier = Modifier.height(kSpace))
                                           ListItem(
                                               colors = ListItemDefaults.colors(
                                                   containerColor = MaterialTheme.colorScheme.background
                                               ),
                                               modifier = Modifier
                                                   .border(
                                                       width = 1.5.dp,
                                                       color = Color.Gray.copy(alpha = 0.2f),
                                                       shape = MaterialTheme.shapes.medium
                                                   )
                                                   .padding(kSpace),
                                               headlineContent = {
                                                   Text("Publication date", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400, fontSize = 16.sp))
                                               },
                                               supportingContent = {
                                                   Text("${viewModel.dataBooks.collectAsState().value?.published_year ?: "NAN"}")
                                               },
                                               leadingContent = {
                                                   Icon(
                                                       imageVector = Icons.Default.DateRange,
                                                       contentDescription = null,
                                                       modifier = Modifier.size(32.dp),
                                                       tint = if(isSystemInDarkTheme()) Color.White else Color.Black
                                                   )
                                               }
                                           )
                                           Spacer(modifier = Modifier.height(kSpace))
                                           ListItem(
                                               colors = ListItemDefaults.colors(
                                                   containerColor = MaterialTheme.colorScheme.background
                                               ),
                                               modifier = Modifier
                                                   .border(
                                                       width = 1.5.dp,
                                                       color = Color.Gray.copy(alpha = 0.2f),
                                                       shape = MaterialTheme.shapes.medium
                                                   )
                                                   .padding(kSpace),
                                               headlineContent = {
                                                   Text("Print Length", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400, fontSize = 16.sp))
                                               },
                                               supportingContent = {
                                                   Text("${viewModel.dataBooks.collectAsState().value?.num_pages ?: "NAN"} pages")
                                               },
                                               leadingContent = {
                                                   Icon(
                                                       imageVector = Icons.Default.Book,
                                                       contentDescription = null,
                                                       modifier = Modifier.size(32.dp),
                                                       tint = if(isSystemInDarkTheme()) Color.White else Color.Black
                                                   )
                                               }
                                           )
                                           Spacer(modifier = Modifier.height(kSpace))
                                           ListItem(
                                               colors = ListItemDefaults.colors(
                                                   containerColor = MaterialTheme.colorScheme.background
                                               ),
                                               modifier = Modifier
                                                   .border(
                                                       width = 1.5.dp,
                                                       color = Color.Gray.copy(alpha = 0.2f),
                                                       shape = MaterialTheme.shapes.medium
                                                   )
                                                   .padding(kSpace),
                                               headlineContent = {
                                                   Text("Rating", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400, fontSize = 16.sp))
                                               },
                                               supportingContent = {
                                                   Text("${viewModel.dataBooks.collectAsState().value?.average_rating ?: "NAN"} (${viewModel.dataBooks.collectAsState().value?.ratings_count ?: "NAN"})")
                                               },
                                               leadingContent = {
                                                   Icon(
                                                       imageVector = Icons.Default.Star,
                                                       contentDescription = null,
                                                       modifier = Modifier.size(32.dp),
                                                       tint = if(isSystemInDarkTheme()) Color.White else Color.Black
                                                   )
                                               }
                                           )
                                       }
                                   }
                               }
                           )
                           TextButton(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .fillMaxHeight(0.45f)
                                   .padding(kPadding),
                               shape = MaterialTheme.shapes.medium,
                               colors = ButtonDefaults.buttonColors(
                                   containerColor = MaterialTheme.colorScheme.primary
                               ),
                               content = {
                                   Text("Add to Collection")
                               },
                               onClick = {
                                   openDialog.value = !openDialog.value
                               }
                           )
                       }
                   )
               }
           }
       )
    }
}


@Composable
fun DialogCollection(shouldShowDialog: MutableState<Boolean>, selectedShelve: MutableState<Shelve?>) {
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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
            },
            text = {
                Column {
                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        headlineContent = {
                            Text("Reading", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400, fontSize = 18.sp))
                        },
                        trailingContent = {
                            RadioButton(
                                selected = selectedShelve.value == Shelve.READING,
                                onClick = { selectedShelve.value = Shelve.READING },
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
                                selected = selectedShelve.value == Shelve.CURRENTLY_READ,
                                onClick = { selectedShelve.value = Shelve.CURRENTLY_READ },
                            )
                        }
                    )
                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        headlineContent = {
                            Text("Want to read", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400, fontSize = 16.sp))
                        },
                        trailingContent = {
                            RadioButton(
                                selected = selectedShelve.value == Shelve.WANT_TO_READ,
                                onClick = { selectedShelve.value = Shelve.WANT_TO_READ },
                            )
                        }
                    )
                }
            },
            confirmButton = { // 6
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                        selectedShelve.value = null
                    }
                ) {
                    Text(
                        text = "Save",
                        color = Color.White
                    )
                }
            }
        )
    }
}