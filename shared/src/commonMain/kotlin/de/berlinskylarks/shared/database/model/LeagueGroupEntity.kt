package de.berlinskylarks.shared.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.League
import de.berlinskylarks.shared.data.model.LeagueGroup

@Entity(tableName = "league_groups")
data class LeagueGroupEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val acronym: String,
    val season: Int,
    @Embedded(prefix = "league_")
    val league: LeagueEntity,
    @Embedded(prefix = "table_")
    val table: TableEntity?,
) {
    fun toLeagueGroup(): LeagueGroup {
        return LeagueGroup(
            id = id,
            name = name,
            acronym = acronym,
            league = League(
                id = league.id,
                name = league.name,
                acronym = league.acronym,
                season = league.season,
                sport = league.sport,
                classification = league.classification,
                ageGroup = league.ageGroup,
            ),
            season = season
        )
    }
}
