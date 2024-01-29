package ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Scoreboard
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material.icons.outlined.TableRows
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class SkylarksNavDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : SkylarksNavDestination(
        route = "home",
        title = "Home",
        icon = Icons.Outlined.Stars
    )
    data object Scores : SkylarksNavDestination(
        route = "scores",
        title = "Scores",
        icon = Icons.Outlined.Scoreboard
    )

    data object ScoresDetail: SkylarksNavDestination(
        route = "scores_detail",
        title = "Game Detail",
        icon = Icons.Outlined.Scoreboard
    ) {
        const val scoreArg = "game_id"
        val routeWithArgs = "${route}/{${scoreArg}}"
        val arguments = listOf(
            navArgument(scoreArg) { type = NavType.IntType }
        )
    }

    data object Standings : SkylarksNavDestination(
        route = "standings",
        title = "Standings",
        icon = Icons.Outlined.TableRows
    )

    data object StandingsDetail: SkylarksNavDestination(
        route = "standings_detail",
        title = "Table Detail",
        icon = Icons.Outlined.TableRows
    ) {
        const val tableArg = "league_group_id"
        val routeWithArgs = "${route}/{${tableArg}}"
        val arguments = listOf(
            navArgument(tableArg) { type = NavType.IntType }
        )
    }
    data object Club : SkylarksNavDestination(
        route = "club",
        title = "Club",
        icon = Icons.Outlined.Shield,
    )
    data object Settings : SkylarksNavDestination(
        route = "settings",
        title = "Settings",
        icon = Icons.Outlined.Settings
    )
}