package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.BSMTeam
import de.berlinskylarks.shared.data.model.LeagueEntry

@Entity(tableName = "bsm_teams")
data class BSMTeamEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val season: Int,
    val pool: Boolean,
    val humanState: String,
    val shortName: String,
    val leagueEntries: List<LeagueEntry>,
) {
    fun toBSMTeam(): BSMTeam {
        return BSMTeam(
            id = id,
            name = name,
            season = season,
            pool = pool,
            humanState = humanState,
            shortName = shortName,
            leagueEntries = leagueEntries
        )
    }
}