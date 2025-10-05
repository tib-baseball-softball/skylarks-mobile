package de.davidbattefeld.berlinskylarks.ui.nav

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey

@Composable
fun SkylarksBottomBar(
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    if (navigationType != NavigationType.BOTTOM_NAVIGATION) {
        return
    }

    NavigationBar {
        TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
            val isSelected = topLevelRoute == topLevelBackStack.topLevelKey
            NavBarItem(
                selected = isSelected,
                screen = topLevelRoute,
                navigationAction = {
                    topLevelBackStack.addTopLevel(topLevelRoute)
                }
            )
        }
    }
}
