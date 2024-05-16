package com.app.readingtracker.pages.profile
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.app.readingtracker.ui.theme.kPadding

class ProfileView: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircleAvatar(name = "Phanith LIM", imageUrl = "https://somoskudasai.com/wp-content/uploads/2022/09/portada_lycoris-recoil-24.jpg")
                        Spacer(modifier = Modifier.height(8.dp))
                        UserDataReadingComposable()
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserDataReadingComposable() {
    Row(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f).padding(horizontal = kPadding),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(
                        text = "104",
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
                        text = "104",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(text = "Read", style = MaterialTheme.typography.titleLarge)
                }
            )
        }
    )
}
