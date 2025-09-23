package de.davidbattefeld.berlinskylarks.ui.scores.box

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.OffensiveMatchStats

@Composable
fun OffensiveTable(teamName: String, matchStats: OffensiveMatchStats) {
    val scrollState = rememberScrollState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                HeaderCell(text = "$teamName (Batters)", minWidth = 180.dp)
                HeaderCell(text = "AB", minWidth = 48.dp)
                HeaderCell(text = "R", minWidth = 48.dp)
                HeaderCell(text = "H", minWidth = 48.dp)
                HeaderCell(text = "RBI", minWidth = 56.dp)
                HeaderCell(text = "K", minWidth = 48.dp)
                HeaderCell(text = "BB", minWidth = 48.dp)
                HeaderCell(text = "AVG", minWidth = 64.dp)
                HeaderCell(text = "OPS", minWidth = 64.dp)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            matchStats.lineup.forEach { player ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val name =
                        "${player.person.lastName}, ${player.person.firstName.firstOrNull() ?: ' '}."
                    val positions = player.humanPositionsShort.joinToString(separator = "-")
                    BodyCell(
                        text = if (positions.isBlank()) name else "$name  $positions",
                        minWidth = 180.dp
                    )
                    BodyCell(text = player.values.atBats.toString(), minWidth = 48.dp)
                    BodyCell(text = player.values.runs.toString(), minWidth = 48.dp)
                    BodyCell(text = player.values.hits.toString(), minWidth = 48.dp)
                    BodyCell(text = player.values.runsBattedIn.toString(), minWidth = 56.dp)
                    BodyCell(text = player.values.strikeouts.toString(), minWidth = 48.dp)
                    BodyCell(text = player.values.baseOnBalls.toString(), minWidth = 48.dp)
                    BodyCell(text = player.values.battingAverage ?: "", minWidth = 64.dp)
                    BodyCell(text = player.values.onBasePlusSlugging ?: "", minWidth = 64.dp)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                BodyCell(text = "", minWidth = 180.dp)
                BodyCell(text = matchStats.sum.atBats.toString(), minWidth = 48.dp)
                BodyCell(text = matchStats.sum.runs.toString(), minWidth = 48.dp)
                BodyCell(text = matchStats.sum.hits.toString(), minWidth = 48.dp)
                BodyCell(text = matchStats.sum.runsBattedIn.toString(), minWidth = 56.dp)
                BodyCell(text = matchStats.sum.strikeouts.toString(), minWidth = 48.dp)
                BodyCell(text = matchStats.sum.baseOnBalls.toString(), minWidth = 48.dp)
                BodyCell(text = "", minWidth = 64.dp)
                BodyCell(text = "", minWidth = 64.dp)
            }
        }
    }
}