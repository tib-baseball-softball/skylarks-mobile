package de.davidbattefeld.berlinskylarks.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Text(text = "Games", style = MaterialTheme.typography.headlineSmall)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(14.dp)
        ) {
            Text(text = "Last Game", style = MaterialTheme.typography.titleSmall)
            if (dataset.lastGame is Game) {
                ScoresItem(
                    gameDecorator = GameDecorator(game = dataset.lastGame!!).decorate(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            } else {
                ContentNotFoundView("last Game")
            }

            Text(text = "Next Game", style = MaterialTheme.typography.titleSmall)
            if (dataset.nextGame is Game) {
                ScoresItem(
                    gameDecorator = GameDecorator(game = dataset.nextGame!!).decorate(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            } else {
                ContentNotFoundView("next Game")
            }
        }
    }
}
