package de.davidbattefeld.berlinskylarks.ui.scores.box

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                HeaderCell(text = "$teamName (Batters)", minWidth = 156.dp)
                HeaderCell(text = "AB", minWidth = 30.dp)
                HeaderCell(text = "R", minWidth = 30.dp)
                HeaderCell(text = "H", minWidth = 30.dp)
                HeaderCell(text = "RBI", minWidth = 30.dp)
                HeaderCell(text = "K", minWidth = 30.dp)
                HeaderCell(text = "BB", minWidth = 30.dp)
                HeaderCell(text = "AVG", minWidth = 50.dp)
                HeaderCell(text = "OPS", minWidth = 50.dp)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp).width(430.dp))
            matchStats.lineup.forEach { player ->
                Row(
                    horizontalArrangement = Arrangement.Start,
                    //verticalAlignment = Alignment.CenterVertically
                ) {
                    var name =
                        "${player.person.lastName}, ${player.person.firstName.firstOrNull() ?: ' '}."
                    if (!player.starter) {
                        name = "   $name"
                    }
                    val positions = player.humanPositionsShort.joinToString(separator = "-").lowercase()
                    BodyCell(
                        text = if (positions.isBlank()) name else "$name  $positions",
                        minWidth = 160.dp
                    )
                    BodyCell(text = player.values.atBats.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.runs.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.hits.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.runsBattedIn.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.strikeouts.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.baseOnBalls.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.battingAverage ?: "", minWidth = 50.dp)
                    BodyCell(text = player.values.onBasePlusSlugging ?: "", minWidth = 50.dp)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp).width(430.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                BodyCell(text = "", minWidth = 156.dp)
                BodyCell(text = matchStats.sum.atBats.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.runs.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.hits.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.runsBattedIn.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.strikeouts.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.baseOnBalls.toString(), minWidth = 30.dp)
                BodyCell(text = "", minWidth = 50.dp)
                BodyCell(text = "", minWidth = 50.dp)
            }
        }
    }
}