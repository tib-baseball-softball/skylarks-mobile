package ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Outlined.Stars
    )
    object Scores : BottomBarScreen(
        route = "scores",
        title = "Scores",
        icon = Icons.Outlined.Scoreboard
    )
    object Standings : BottomBarScreen(
        route = "standings",
        title = "Standings",
        icon = Icons.Outlined.TableRows
    )
    object Club : BottomBarScreen(
        route = "club",
        title = "Club",
        icon = Icons.Outlined.Shield,
    )
    object Settings : BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = Icons.Outlined.Settings
    )
}