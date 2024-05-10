package com.app.readingtracker.pages.profile
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.SubcomposeAsyncImage
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kPrimary

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
                    title = {
                        Text("Profile")
                    },
                    actions = {
                        IconButton(
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Color.Gray.copy(alpha = 0.2f),
                            ),
                            onClick = {

                            }
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = null)
                        }
                    }
                )
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize().padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircleAvatar(name = "Phanith LIM", imageUrl = "https://somoskudasai.com/wp-content/uploads/2022/09/portada_lycoris-recoil-24.jpg")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        )
    }

}

@Composable
fun CircleAvatar(name: String, imageUrl: String? = null) {
    if(imageUrl != null) {
        return Column (horizontalAlignment = Alignment.CenterHorizontally,) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(kPrimary.copy(alpha = 0.3f))
                    .aspectRatio(1f)
            ) {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    loading = {
                        CircularProgressIndicator()
                    },
                    filterQuality = FilterQuality.Low,
                    clipToBounds = true,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                color = kPrimary,
                textAlign = TextAlign.Center,
            )
        }
   }
   return Column (horizontalAlignment = Alignment.CenterHorizontally,){
       Box(
           modifier = Modifier
               .size(96.dp)
               .clip(CircleShape)
               .background(kPrimary.copy(alpha = 0.3f))
               .aspectRatio(1f)
       ) {
           Icon(
               Icons.Default.Person,
               contentDescription = null,
               tint = kPrimary,
               modifier = Modifier.align(Alignment.Center).padding(kPadding / 4).size(48.dp),
           )
       }
       Spacer(modifier = Modifier.height(8.dp))
       Text(
           text = name,
           style = MaterialTheme.typography.titleLarge,
           color = kPrimary,
           textAlign = TextAlign.Center,
       )
   }
}