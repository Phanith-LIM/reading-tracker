package com.app.readingtracker.pages.sign_in
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.ExperimentalSafeArgsApi
import com.app.readingtracker.R
import com.app.readingtracker.ui.theme.kPadding
import com.app.readingtracker.ui.theme.kPrimary
import com.app.readingtracker.ui.theme.kSecondary
import com.app.readingtracker.ui.theme.kSpace

@OptIn(ExperimentalSafeArgsApi::class)
@Composable
fun SignInView() {
    return Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
        content = { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.padding(paddingValues),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "logo app",
                        modifier = Modifier.width(150.dp).height(150.dp)
                    )
                }
                Spacer(modifier = Modifier.height(kSpace))
                Text(
                    text = "Welcome",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = if(isSystemInDarkTheme()) {
                        kSecondary
                    } else kPrimary
                )
                Spacer(modifier = Modifier.height(kSpace))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Create account and access thousand\n" +
                            "of cool stuff",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if(isSystemInDarkTheme()) {
                        Color.White
                    } else Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    modifier = Modifier.fillMaxWidth(0.7f).height(50.dp).padding(horizontal = kPadding * 2),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = MaterialTheme.shapes.medium,
                    content = {
                        Icon(
                            painter = painterResource(R.drawable.google_icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(kSpace))
                        Text(
                            text = "Continue with Google",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 16.sp
                            ),
                            color = Color.White
                        )
                    },
                    onClick = {
                    }
                )
                Spacer(modifier = Modifier.height(kSpace))
                TextButton(
                    modifier = Modifier.fillMaxWidth(0.7f).height(50.dp).padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = kPrimary
                    ),
                    shape = MaterialTheme.shapes.medium,
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Facebook,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(kSpace))
                        Text(
                            text= "Continue with Facebook",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 16.sp
                            ),
                            color = Color.White,
                        )
                    },
                    onClick = {

                    }
                )
            }
        }
    )
}
