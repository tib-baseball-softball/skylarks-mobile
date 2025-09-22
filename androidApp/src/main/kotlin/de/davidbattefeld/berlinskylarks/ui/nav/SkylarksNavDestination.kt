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

val TOP_LEVEL_ROUTES: List<TopLevelDestination> = listOf(Home, Scores, Standings, Club, Settings)

/**
 * Sealed interface defining the contract for all navigation destinations.
 */
sealed interface SkylarksNavDestination : NavKey {
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
    override val title = "Home"
    override val icon = Icons.Outlined.Stars
}

@Serializable
data object Scores : TopLevelDestination {
    override val title = "Scores"
    override val icon = Icons.Outlined.Scoreboard
}

@Serializable
data class ScoresDetail(val id: Int) : SkylarksNavDestination {
    override val title = "Game Detail"

    companion object {
        val title = "Game Detail"
    }
}

@Serializable
data object Standings : TopLevelDestination {
    override val title = "Standings"
    override val icon = Icons.Outlined.TableRows
}

@Serializable
data class StandingsDetail(val id: Int) : SkylarksNavDestination {
    override val title = "Table Detail"

    companion object {
        val title = "Table Detail"
    }
}

@Serializable
data object Club : TopLevelDestination {
    override val title = "Club"
    override val icon = Icons.Outlined.Shield
}

@Serializable
data object Teams : TopLevelDestination {
    override val title = "Teams"
    override val icon = Icons.Outlined.Groups
}

@Serializable
data class TeamDetail(val id: Int) : SkylarksNavDestination {
    override val title = "Team Detail"

    companion object {
        val title = "Team Detail"
    }
}

@Serializable
data class PlayerDetail(val id: Int) : SkylarksNavDestination {
    override val title = "Player Detail"

    companion object {
        val title = "Player Detail"
    }
}

@Serializable
data object Functionary : SkylarksNavDestination {
    override val title = "Team Officials"
}

@Serializable
data object Settings : TopLevelDestination {
    override val title = "Settings"
    override val icon = Icons.Outlined.Settings
}

@Serializable
data object Info : SkylarksNavDestination {
    override val title = "App Info"
}

@Serializable
data object Privacy : SkylarksNavDestination {
    override val title = "Privacy Policy"
}

@Serializable
data object LegalNotice : SkylarksNavDestination {
    override val title = "Legal Notice"
}