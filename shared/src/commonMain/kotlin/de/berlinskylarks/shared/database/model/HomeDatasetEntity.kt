package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.HomeDataset

@Entity(tableName = "home_datasets")
data class HomeDatasetEntity(
    @PrimaryKey
    val teamID: Int,
    val json: HomeDataset
)
