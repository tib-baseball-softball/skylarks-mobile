package de.davidbattefeld.berlinskylarks.ui.nav

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NavSidebarItem(
    screen: TopLevelDestination,
    navigationAction: () -> Unit,
    selected: Boolean,
    navigationType: NavigationType,
) {
    val label = @Composable { Text(text = screen.title) }
    val icon = @Composable {
        Icon(
            imageVector = screen.icon,
            contentDescription = "navigation icon for ${screen.title}"
        )
    }

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
    screen: TopLevelDestination,
    selected: Boolean,
    navigationAction: () -> Unit,
) {
    val label = @Composable { Text(text = screen.title) }
    val icon = @Composable {
        Icon(
            imageVector = screen.icon,
            contentDescription = "navigation icon for ${screen.title}"
        )
    }

    NavigationBarItem(
        label = label,
        icon = icon,
        selected = selected,
        onClick = { navigationAction() },
        alwaysShowLabel = true,
    )
}