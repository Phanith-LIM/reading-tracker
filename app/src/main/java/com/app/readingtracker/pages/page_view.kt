package com.app.readingtracker.pages
import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.readingtracker.ui.theme.kBgPrimary
import com.app.readingtracker.ui.theme.kPrimary
import com.app.readingtracker.ui.theme.kSecondary

data class BottomNavigationItem (
    val title: String,
    val selectIcon: ImageVector,
    val unselectIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageView() {

    val itemList =  listOf(
        BottomNavigationItem(
            title = "Home",
            selectIcon = Icons.Filled.Home,
            unselectIcon = Icons.Outlined.Home,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Book",
            selectIcon = Icons.Filled.Email,
            unselectIcon = Icons.Outlined.Email,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Chart",
            selectIcon = Icons.Filled.Settings,
            unselectIcon = Icons.Outlined.Settings,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Chart",
            selectIcon = Icons.Filled.AccountCircle,
            unselectIcon = Icons.Outlined.AccountCircle,
            hasNews = false,
        )

    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    return Scaffold (
        content = {
            Box(
                modifier = Modifier.padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "HomeView")
            }
        },
        bottomBar = {
            NavigationBar (
                containerColor = kBgPrimary
            ) {
                itemList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        alwaysShowLabel = true,
                        selected = selectedIndex == index,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = kSecondary
                        ),
                        label = {
                          Text(text = item.title)
                        },
                        onClick = {
                            selectedIndex = index
//                            navController.navigate(bottomNavigationItem.title)
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
                                    imageVector = if(selectedIndex == index) {
                                        item.selectIcon
                                    } else item.unselectIcon,
                                    contentDescription = null,
                                )
                            }
                        }
                    )
                }
            }
        }
    )
}