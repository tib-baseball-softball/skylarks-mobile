package de.davidbattefeld.berlinskylarks.data.preferences

import de.berlinskylarks.shared.utility.BSMUtility
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val season: Int,
    val favoriteTeamID: Int = BSMUtility.NON_EXISTENT_ID,
)
