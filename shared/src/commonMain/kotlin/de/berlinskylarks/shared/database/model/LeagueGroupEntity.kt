package de.berlinskylarks.shared.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.GameClass
import de.berlinskylarks.shared.data.model.LeagueGroup

@Entity(tableName = "league_groups")
data class LeagueGroupEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val acronym: String,
    val classification: String?,
    val humanClassification: String?,
    val humanClassificationShort: String?,
    val sport: String,
    val humanSport: String,
    val humanSportShort: String,
    val ageGroup: String,
    val humanAgeGroupShort: String,
    val plannedInnings: Long,
    val season: Int,
    @Embedded(prefix = "game_class_")
    val gameClass: GameClassEntity,
    @Embedded(prefix = "table_")
    val table: TableEntity?,
) {
    fun toLeagueGroup(): LeagueGroup {
        return LeagueGroup(
            id = id,
            name = name,
            acronym = acronym,
            gameClass = GameClass(
                id = gameClass.id,
                name = gameClass.name,
                acronym = gameClass.acronym,
                season = gameClass.season,
                sport = gameClass.sport,
                classification = gameClass.classification,
                ageGroup = gameClass.ageGroup,
            ),
            season = season,
            classification = classification,
            humanClassification = humanClassification,
            humanClassificationShort = humanClassificationShort,
            sport = sport,
            humanSport = humanSport,
            humanSportShort = humanSportShort,
            ageGroup = ageGroup,
            plannedInnings = plannedInnings,
            humanAgeGroupShort = humanAgeGroupShort,
        )
    }
}
