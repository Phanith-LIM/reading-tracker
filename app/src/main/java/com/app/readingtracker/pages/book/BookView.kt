package com.app.readingtracker.pages.book

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.app.readingtracker.ui.theme.kPadding

@OptIn(ExperimentalMaterial3Api::class)
class BookView: Screen {
    @Composable
    override fun Content() {
        return Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                    title = { Text("Books", textAlign = TextAlign.Center) },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        }
                    }
                )
            },
            content = { it ->
                Column (
                    modifier = Modifier.fillMaxSize().padding(it).padding(kPadding * 2).verticalScroll(rememberScrollState()),
                ) {
                    Text("Shelves", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(kPadding / 2 ))
                    ListTile(title = "Want to Read", subTitle = "0 books", icon = Icons.Filled.Book)
                    Spacer(modifier = Modifier.height(kPadding /2 ))
                    ListTile(title = "Currently Reading", subTitle = "0 books", icon = Icons.Filled.MyLocation)
                    Spacer(modifier = Modifier.height(kPadding / 2))
                    ListTile(title = "Read", subTitle = "0 books", icon = Icons.Filled.CheckCircle)
                }
            }
        )
    }
}


@Composable
fun ListTile(title: String, subTitle: String, icon: ImageVector) {
    return ListItem(
        headlineContent = { Text(title) },
        modifier = Modifier.padding(vertical = kPadding).border(BorderStroke(width = 1.dp, color = Color.LightGray), shape = MaterialTheme.shapes.large).height(100.dp),
        supportingContent = { Text(subTitle) },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        leadingContent = {
            Icon(
                icon,
                modifier = Modifier.size(40.dp),
                contentDescription = "Localized description",
            )
        }
    )
}