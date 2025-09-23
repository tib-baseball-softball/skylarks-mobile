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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Linescore

@Composable
fun LinescoreTable(linescore: Linescore, cellWidth: Dp = 40.dp) {
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
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                HeaderCell(text = "", minWidth = 120.dp)
                for (inning in 1..linescore.playedInnings) {
                    HeaderCell(text = inning.toString(), minWidth = cellWidth)
                }
                HeaderCell(text = "R", minWidth = cellWidth)
                HeaderCell(text = "H", minWidth = cellWidth)
                HeaderCell(text = "E", minWidth = cellWidth)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            // Away row
            Row(verticalAlignment = Alignment.CenterVertically) {
                BodyCell(
                    text = linescore.away.leagueEntry.team.name,
                    minWidth = 120.dp,
                    bold = true
                )
                linescore.away.innings.forEach { valStr ->
                    BodyCell(
                        text = valStr.toString(),
                        minWidth = cellWidth
                    )
                }
                BodyCell(text = linescore.away.runs.toString(), minWidth = cellWidth, bold = true)
                BodyCell(text = linescore.away.hits.toString(), minWidth = cellWidth)
                BodyCell(text = linescore.away.errors.toString(), minWidth = cellWidth)
            }
            // Home row
            Row(verticalAlignment = Alignment.CenterVertically) {
                BodyCell(
                    text = linescore.home.leagueEntry.team.name,
                    minWidth = 120.dp,
                    bold = true
                )
                linescore.home.innings.forEach { valStr ->
                    BodyCell(
                        text = valStr.toString(),
                        minWidth = cellWidth
                    )
                }
                BodyCell(text = linescore.home.runs.toString(), minWidth = cellWidth, bold = true)
                BodyCell(text = linescore.home.hits.toString(), minWidth = cellWidth)
                BodyCell(text = linescore.home.errors.toString(), minWidth = cellWidth)
            }
        }
    }
}