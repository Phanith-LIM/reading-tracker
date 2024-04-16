package com.app.readingtracker.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.readingtracker.pages.PageRoute.*
import com.app.readingtracker.pages.book.BookView
import com.app.readingtracker.pages.home.HomeView

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeView.route) {
        composable(route = HomeView.route) {
            HomeView()
        }
        composable(route = BookView.route) {
           BookView()
        }
        composable(route = ChartView.route) {
            ChartView()
        }
        composable(route = ProfileView.route) {
            ProfileView()
        }
    }
}

@Composable
fun ChartView () {
    return Scaffold (
        content = {
            Box(
                modifier = Modifier.padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "ChartView")
            }
        }
    )
}


@Composable
fun ProfileView () {
    return Scaffold (
        content = {
            Box(
                modifier = Modifier.padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "ProfileView")
            }
        }
    )
}

