package com.app.readingtracker.pages.splashscreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.app.readingtracker.R
import com.app.readingtracker.pages.PageView
import com.app.readingtracker.pages.sign_in.SignInView
import kotlinx.coroutines.delay

class SplashScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = viewModel<SplashScreenViewModel>()
        val context = LocalContext.current

        LaunchedEffect(viewModel) {
            delay(500)
            val hasToken = viewModel.checkToken(context)
            if (hasToken) {
                navigator.replaceAll(PageView())
            } else {
                navigator.replaceAll(SignInView())
            }
        }

        Scaffold (
            containerColor = MaterialTheme.colorScheme.background,
            content = { it ->
                Box(
                    modifier = Modifier.fillMaxSize().padding(it),
                    contentAlignment = Alignment.Center,
                    content = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            content = {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "logo app",
                                    modifier = Modifier
                                        .width(125.dp)
                                        .height(125.dp)
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                                CircularProgressIndicator()
                            }
                        )
                    }
                )
            }
        )
    }
}
