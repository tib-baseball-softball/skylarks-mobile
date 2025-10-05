package de.davidbattefeld.berlinskylarks.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey

@Composable
fun SkylarksSidebarNavigation(
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
        val isSelected = topLevelRoute == topLevelBackStack.topLevelKey
        NavSidebarItem(
            screen = topLevelRoute,
            navigationAction = {
                topLevelBackStack.addTopLevel(topLevelRoute)
            },
            navigationType = navigationType,
            selected = isSelected,
        )
    }
}