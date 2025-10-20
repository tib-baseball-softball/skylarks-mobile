package de.davidbattefeld.berlinskylarks.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Game
import de.berlinskylarks.shared.data.model.HomeDataset
import de.davidbattefeld.berlinskylarks.domain.service.GameDecorator
import de.davidbattefeld.berlinskylarks.ui.scores.ScoresItem
import de.davidbattefeld.berlinskylarks.ui.standings.StandingsRowInfoCard
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView

@Composable
fun HomeDatasetItem(
    dataset: HomeDataset,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StandingsRowInfoCard(
            name = dataset.leagueGroup.name,
            acronym = dataset.leagueGroup.acronym,
            record = "${dataset.tableRow?.wins_count?.toInt()} - ${dataset.tableRow?.losses_count?.toInt()}",
            percentage = dataset.tableRow?.quota ?: "",
            rank = dataset.tableRow?.rank ?: "",
            backgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
        OutlinedButton(
            onClick = {}
        ) {
            Text(text = "Go to full standings")
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (dataset.lastGame != null || dataset.nextGame != null) {
            Text(text = "Games", style = MaterialTheme.typography.headlineMedium)
        }

        if (dataset.lastGame is Game) {
            Text(text = "Last Game", style = MaterialTheme.typography.headlineSmall)
            ScoresItem(gameDecorator = GameDecorator(game = dataset.lastGame!!).decorate())
        } else {
            ContentNotFoundView("last Game")
        }

        if (dataset.nextGame is Game) {
            Text(text = "Next Game", style = MaterialTheme.typography.headlineSmall)
            ScoresItem(gameDecorator = GameDecorator(game = dataset.nextGame!!).decorate())
        } else {
            ContentNotFoundView("next Game")
        }
    }
}