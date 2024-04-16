package com.app.readingtracker.pages.sign_in

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.readingtracker.pages.sign_in.SignInRoute.*
import com.app.readingtracker.pages.PageView

@Composable
fun SignInNavigationHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SignInView.route) {
        composable(route = SignInView.route) {
            SignInView(navController)
        }
        composable(route = PageView.route) {
            PageView()
        }
    }
}