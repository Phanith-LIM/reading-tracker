package com.app.readingtracker.pages.sign_in
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
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
import com.app.readingtracker.R
import com.app.readingtracker.ui.theme.kBgPrimary
import com.app.readingtracker.ui.theme.kPrimary

@Composable
fun SignInView() {
    Scaffold(
        containerColor = kBgPrimary,
        content = { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally, // Change here
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Welcome",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W600,
                    color = kPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Create account and access thousand\n" +
                            "of cool stuff",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    modifier = Modifier.fillMaxWidth(0.7f).height(50.dp).padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = kPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    content = {
                        Icon(
                            painter = painterResource(R.drawable.google_icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Continue with Google",fontSize = 16.sp, color = Color.White)
                    },
                    onClick = {

                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    modifier = Modifier.fillMaxWidth(0.7f).height(50.dp).padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = kPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Facebook,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Continue with Facebook",fontSize = 16.sp, color = Color.White)
                    },
                    onClick = {

                    }
                )
            }
        }
    )
}
