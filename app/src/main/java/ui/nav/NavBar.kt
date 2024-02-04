package ui.nav

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavBar(navController: NavHostController) {
    val screens = listOf(
        SkylarksNavDestination.Home,
        SkylarksNavDestination.Scores,
        SkylarksNavDestination.Standings,
        SkylarksNavDestination.Club,
        SkylarksNavDestination.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        //containerColor = MaterialTheme.colors.primary,
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: SkylarksNavDestination,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = { Text(text = screen.title) },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any { current ->
            current.route?.contains(screen.route, ignoreCase = true) ?: false
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        alwaysShowLabel = true,
    )
}