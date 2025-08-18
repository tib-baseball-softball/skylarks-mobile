package de.davidbattefeld.berlinskylarks.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Scoreboard
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material.icons.outlined.TableRows
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

val TOP_LEVEL_ROUTES: List<TopLevelDestination> = listOf(Scores, Standings, Club, Settings)

/**
 * Sealed interface defining the contract for all navigation destinations.
 */
sealed interface SkylarksNavDestination : NavKey {
    val route: String
    val title: String
}

/**
 * Top level destinations need an icon.
 */
sealed interface TopLevelDestination : SkylarksNavDestination {
    val icon: ImageVector
}

@Serializable
data object Home : TopLevelDestination {
    override val route = "home"
    override val title = "Home"
    override val icon = Icons.Outlined.Stars
}

@Serializable
data object Scores : TopLevelDestination {
    override val route = "scores"
    override val title = "Scores"
    override val icon = Icons.Outlined.Scoreboard
}

@Serializable
data class ScoresDetail(val id: Int) : SkylarksNavDestination {
    override val route = "scores_detail"
    override val title = "Game Detail"

    companion object {
        val title = "Game Detail"
    }
}

@Serializable
data object Standings : TopLevelDestination {
    override val route = "standings"
    override val title = "Standings"
    override val icon = Icons.Outlined.TableRows
}

@Serializable
data class StandingsDetail(val id: Int) : SkylarksNavDestination {
    override val route = "standings_detail"
    override val title = "Table Detail"

    companion object {
        val title = "Table Detail"
    }
}

@Serializable
data object Club : TopLevelDestination {
    override val route = "club"
    override val title = "Club"
    override val icon = Icons.Outlined.Shield
}

@Serializable
data object Teams : TopLevelDestination {
    override val route = "club_teams"
    override val title = "Teams"
    override val icon = Icons.Outlined.Groups
}

@Serializable
data class TeamDetail(val id: Int) : SkylarksNavDestination {
    override val route = "club_player_list"
    override val title = "Team Detail"

    companion object {
        val title = "Team Detail"
    }
}

@Serializable
data class PlayerDetail(val id: Int) : SkylarksNavDestination {
    override val route = "club_player_detail"
    override val title = "Player Detail"

    companion object {
        val title = "Player Detail"
    }
}

@Serializable
data object Functionary : SkylarksNavDestination {
    override val route = "functionary"
    override val title = "Functionaries"
}

@Serializable
data object Settings : TopLevelDestination {
    override val route = "settings"
    override val title = "Settings"
    override val icon = Icons.Outlined.Settings
}

@Serializable
data object Info : SkylarksNavDestination {
    override val route = "settings_info"
    override val title = "App Info"
}

@Serializable
data object Privacy : SkylarksNavDestination {
    override val route = "settings_privacy"
    override val title = "Privacy Policy"
}

@Serializable
data object LegalNotice : SkylarksNavDestination {
    override val route = "settings_legal"
    override val title = "Legal Notice"
}