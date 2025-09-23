package de.davidbattefeld.berlinskylarks.ui.scores.box

import androidx.compose.foundation.horizontalScroll
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
import de.berlinskylarks.shared.data.model.PitchingMatchStats

@Composable
fun PitchingTable(teamName: String, matchStats: PitchingMatchStats) {
    val scrollState = rememberScrollState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
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
                HeaderCell(text = "$teamName (Pitchers)", minWidth = 156.dp)
                HeaderCell(text = "IP", minWidth = 30.dp)
                HeaderCell(text = "BF", minWidth = 30.dp)
                HeaderCell(text = "AB", minWidth = 30.dp)
                HeaderCell(text = "H", minWidth = 30.dp)
                HeaderCell(text = "R", minWidth = 30.dp)
                HeaderCell(text = "ER", minWidth = 30.dp)
                HeaderCell(text = "K", minWidth = 30.dp)
                HeaderCell(text = "BB", minWidth = 30.dp)
                HeaderCell(text = "ERA", minWidth = 50.dp)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp).width(440.dp))
            matchStats.lineup.forEach { player ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val name = "${player.person.lastName}, ${player.person.firstName.firstOrNull() ?: ' '}."
                    val wls = player.values.winLossSave?.let { " ($it)" } ?: ""
                    BodyCell(text = name + wls, minWidth = 150.dp)
                    BodyCell(text = player.values.inningsPitched, minWidth = 30.dp)
                    BodyCell(text = player.values.battersFaced.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.atBats.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.hits.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.runs.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.earnedRuns.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.strikeouts.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.baseOnBallsAllowed.toString(), minWidth = 30.dp)
                    BodyCell(text = player.values.earnedRunsAverage ?: "", minWidth = 50.dp)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp).width(440.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                BodyCell(text = "", minWidth = 150.dp)
                BodyCell(text = matchStats.sum.inningsPitched, minWidth = 30.dp)
                BodyCell(text = matchStats.sum.battersFaced.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.atBats.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.hits.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.runs.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.earnedRuns.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.strikeouts.toString(), minWidth = 30.dp)
                BodyCell(text = matchStats.sum.baseOnBallsAllowed.toString(), minWidth = 30.dp)
                BodyCell(text = "", minWidth = 50.dp)
            }
        }
    }
}