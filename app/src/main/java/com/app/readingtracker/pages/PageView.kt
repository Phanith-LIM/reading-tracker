package com.app.readingtracker.pages

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.app.readingtracker.ui.theme.kSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageView() {
    val navController = rememberNavController()
    val itemList =  listOf(
        BottomNavigationItem(
            title = "Home",
            selectIcon = Icons.Filled.Home,
            unselectIcon = Icons.Outlined.Home,
            hasNews = false,
            route = PageRoute.HomeView.route
        ),
        BottomNavigationItem(
            title = "Book",
            selectIcon = Icons.Filled.Book,
            unselectIcon = Icons.Outlined.Book,
            hasNews = false,
            route = PageRoute.BookView.route
        ),
        BottomNavigationItem(
            title = "Chart",
            selectIcon = Icons.Filled.BarChart,
            unselectIcon = Icons.Outlined.BarChart,
            hasNews = false,
            route = PageRoute.ChartView.route
        ),
        BottomNavigationItem(
            title = "Profile",
            selectIcon = Icons.Filled.AccountCircle,
            unselectIcon = Icons.Outlined.AccountCircle,
            hasNews = false,
            route = PageRoute.ProfileView.route
        )

    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    Scaffold (
        contentColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar (
                containerColor = MaterialTheme.colorScheme.background,
            ) {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentDirection = navBackStackEntry?.destination
                itemList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        selected = selectedIndex == index,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = kSecondary
                        ),
                        label = {
                          Text(text = item.title)
                        },
                        onClick = {
                            selectedIndex = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if(item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else if(item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if(selectedIndex == index) { item.selectIcon
                                    } else item.unselectIcon,
                                    contentDescription = null,
                                    tint = if(isSystemInDarkTheme()) {
                                        if(selectedIndex == index) {
                                            androidx.compose.ui.graphics.Color.Black
                                        } else androidx.compose.ui.graphics.Color.White
                                    } else LocalContentColor.current
                                )
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box (
            modifier = Modifier.padding(paddingValues)
        ) {
            NavigationHost(navController)
        }
    }
}

