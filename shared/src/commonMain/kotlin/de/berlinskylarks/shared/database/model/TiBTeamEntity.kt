package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.tib.SkylarksTeam

@Entity(tableName = "tib_teams")
data class TiBTeamEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val leagueID: Int?,
    val bsmShortName: String?,
    val sport: String,
    val ageGroup: String,
) {
    fun toTeam(): SkylarksTeam {
        return SkylarksTeam(
            id = id,
            name = name,
            leagueID = leagueID,
            bsmShortName = bsmShortName,
            sport = sport,
            ageGroup = ageGroup
        )
    }
}
