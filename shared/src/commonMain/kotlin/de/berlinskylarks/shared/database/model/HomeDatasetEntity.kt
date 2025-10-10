package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import de.berlinskylarks.shared.data.model.HomeDataset

@Entity(
    tableName = "home_datasets",
    primaryKeys = ["teamID", "season", "leagueGroupID"]
)
 data class HomeDatasetEntity(
    val teamID: Int,
    val season: Int,
    val leagueGroupID: Int,
    val json: HomeDataset
) {
    fun toHomeDataset(): HomeDataset {
        return json
    }
}
