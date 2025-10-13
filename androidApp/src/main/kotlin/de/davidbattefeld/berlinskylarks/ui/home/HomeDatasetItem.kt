package de.davidbattefeld.berlinskylarks.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.berlinskylarks.shared.data.model.HomeDataset

@Composable
fun HomeDatasetItem(
    dataset: HomeDataset,
) {
    Column {
        Text(text = dataset.teamID.toString())
        Text(text = dataset.season.toString())
        Text(text = dataset.leagueGroupID.toString())
        Text(text = dataset.leagueGroup.acronym)
    }
}