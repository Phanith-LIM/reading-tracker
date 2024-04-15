package com.app.readingtracker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Phanith LIM"
                    nullable = true
                }
            )
        ) {entry ->
            DetailScreen(name = entry.arguments?.getString("name"))
        }
    }
}


@Composable
fun MainScreen(navController: NavController) {
    var inputText by remember {
        mutableStateOf("")
    }
    Column (
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 50.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputText,
            onValueChange = {inputText = it}
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                println(inputText)
                navController.navigate(Screen.DetailScreen.withArgs(inputText))
            }
        ) {
            Text(text = "To Detail Screen")
        }
    }
}

@Composable
fun DetailScreen (name: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Hello, $name",
            color = Color.Red
        )
    }
}