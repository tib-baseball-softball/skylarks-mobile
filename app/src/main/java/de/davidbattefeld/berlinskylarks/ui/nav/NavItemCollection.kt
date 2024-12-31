package de.davidbattefeld.berlinskylarks.ui.nav

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavItemCollection(
    navController: NavHostController,
    navigationType: NavigationType,
) {
    val screens = listOf(
        //SkylarksNavDestination.Home,
        SkylarksNavDestination.Scores,
        SkylarksNavDestination.Standings,
        SkylarksNavDestination.Club,
        SkylarksNavDestination.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    when (navigationType) {
        NavigationType.BOTTOM_NAVIGATION -> {
            NavigationBar {
                screens.forEach { screen ->
                    NavBarItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navigationAction = {
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
                    )
                }
            }
        }

        else -> {
            screens.forEach { screen ->
                NavSidebarItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navigationAction = {
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
                    navigationType = navigationType
                )
            }
        }
    }


}

@Composable
fun NavSidebarItem(
    screen: SkylarksNavDestination,
    currentDestination: NavDestination?,
    navigationAction: () -> Unit,
    navigationType: NavigationType,
) {
    val label = @Composable { Text(text = screen.title) }
    val icon = @Composable {
        Icon(
            imageVector = screen.icon,
            contentDescription = "navigation icon for ${screen.title}"
        )
    }
    val selected = currentDestination?.hierarchy?.any { current ->
        current.route?.contains(screen.route, ignoreCase = true) ?: false
    } == true

    when (navigationType) {
        NavigationType.NAVIGATION_RAIL -> {
            NavigationRailItem(
                label = label,
                icon = icon,
                selected = selected,
                onClick = { navigationAction() },
                alwaysShowLabel = true,
            )
        }

        NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            NavigationDrawerItem(
                label = label,
                icon = icon,
                selected = selected,
                onClick = { navigationAction() },
            )
        }

        else -> {
            Text("Something went very wrong. This shouldn't happen.")
        }
    }
}

/**
 * Sadly NavigationBarItem is fixed to RowScope, unlike the other two item types. Duplication necessary.
 */
@Composable
fun RowScope.NavBarItem(
    screen: SkylarksNavDestination,
    currentDestination: NavDestination?,
    navigationAction: () -> Unit,
) {
    val label = @Composable { Text(text = screen.title) }
    val icon = @Composable {
        Icon(
            imageVector = screen.icon,
            contentDescription = "navigation icon for ${screen.title}"
        )
    }
    val selected = currentDestination?.hierarchy?.any { current ->
        current.route?.contains(screen.route, ignoreCase = true) ?: false
    } == true

    NavigationBarItem(
        label = label,
        icon = icon,
        selected = selected,
        onClick = { navigationAction() },
        alwaysShowLabel = true,
    )
}