package com.app.readingtracker.pages
import  android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.app.readingtracker.pages.book.BookView
import com.app.readingtracker.pages.chart.ChartView
import com.app.readingtracker.pages.home.HomeView
import com.app.readingtracker.pages.profile.ProfileView
import com.app.readingtracker.ui.theme.kSecondary

class PageView: Screen {
    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = viewModel<PageViewModel>()
        val itemList =  listOf(
            BottomNavigationItem(
                title = "Home",
                selectIcon = Icons.Filled.Home,
                unselectIcon = Icons.Outlined.Home,
                hasNews = false,
            ),
            BottomNavigationItem(
                title = "Book",
                selectIcon = Icons.Filled.Book,
                unselectIcon = Icons.Outlined.Book,
                hasNews = false,
            ),
            BottomNavigationItem(
                title = "Chart",
                selectIcon = Icons.Filled.BarChart,
                unselectIcon = Icons.Outlined.BarChart,
                hasNews = false,
            ),
            BottomNavigationItem(
                title = "Profile",
                selectIcon = Icons.Filled.AccountCircle,
                unselectIcon = Icons.Outlined.AccountCircle,
                hasNews = false,
            )
        )

        val navigator = LocalNavigator.current
        return Scaffold (
            contentColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = WindowInsets(0.dp),
            content = {
                Box(modifier = Modifier.fillMaxSize().padding(it)) {
                    when (viewModel.selectedIndex.collectAsState().value) {
                        0 -> Navigator(HomeView(navigator = navigator))
                        1 -> Navigator(BookView(navigator = navigator))
                        2 -> Navigator(ChartView())
                        3 -> Navigator(ProfileView(navigator = navigator))
                    }
                }
            },
            bottomBar = {
                NavigationBar (
                    containerColor = MaterialTheme.colorScheme.background,
                ) {
                    itemList.forEachIndexed { index, item ->
                        NavigationBarItem(
                            alwaysShowLabel = true,
                            selected = viewModel.selectedIndex.collectAsState().value == index,
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = kSecondary
                            ),
                            label = {
                                Text(text = item.title)
                            },
                            onClick = {
                                viewModel.setSelectedIndex(index)
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
                                        imageVector = if(viewModel.selectedIndex.value == index) { item.selectIcon } else item.unselectIcon,
                                        contentDescription = null,
                                        tint = if(isSystemInDarkTheme()) {
                                            if(viewModel.selectedIndex.value == index) {
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
        )
    }

}