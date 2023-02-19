package com.etu.booking.compose.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.List
import androidx.compose.material.icons.twotone.MoreVert
import androidx.compose.material.icons.twotone.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationBar(
    navController: NavHostController = rememberNavController(),
) {
    val screensWithIcons = mapOf(
        Screen.Search to Icons.TwoTone.Search,
        Screen.History to Icons.TwoTone.List,
        Screen.Person to Icons.TwoTone.AccountCircle,
        Screen.More to Icons.TwoTone.MoreVert,
    )

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screensWithIcons.forEach { (screen, icon) ->
                    BottomNavigationItem(
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy
                            ?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavigationController(innerPadding = innerPadding, navController = navController)
    }
}
