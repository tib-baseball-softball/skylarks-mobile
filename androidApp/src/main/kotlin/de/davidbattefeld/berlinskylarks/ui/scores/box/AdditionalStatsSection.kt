package de.davidbattefeld.berlinskylarks.ui.scores.box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.AdditionalMatchStats

@Composable
fun AdditionalStatsSection(stats: AdditionalMatchStats) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            "BATTING",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        if (stats.batting.doubles.isNotEmpty())
            LabeledStatRow(label = "2B:", entries = stats.batting.doubles)
        if (stats.batting.triples.isNotEmpty())
            LabeledStatRow(label = "3B:", entries = stats.batting.triples)
        if (stats.batting.homeruns.isNotEmpty())
            LabeledStatRow(label = "HR:", entries = stats.batting.homeruns)
        if (stats.batting.sacrificeHits.isNotEmpty())
            LabeledStatRow(label = "SH:", entries = stats.batting.sacrificeHits)
        if (stats.batting.sacrificeFlys.isNotEmpty())
            LabeledStatRow(label = "SF:", entries = stats.batting.sacrificeFlys)

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "BASERUNNING",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        if (stats.baserunning.stolenBases.isNotEmpty())
            LabeledStatRow(label = "SB:", entries = stats.baserunning.stolenBases)
        if (stats.baserunning.caughtStealings.isNotEmpty())
            LabeledStatRow(label = "CS:", entries = stats.baserunning.caughtStealings)

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "FIELDING",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        if (stats.fielding.errors.isNotEmpty())
            LabeledStatRow(label = "E:", entries = stats.fielding.errors)
        if (stats.fielding.passedBalls.isNotEmpty())
            LabeledStatRow(label = "PB:", entries = stats.fielding.passedBalls)
        if (stats.fielding.doublePlays.isNotEmpty())
            LabeledStatRow(label = "DP:", entries = stats.fielding.doublePlays)
        if (stats.fielding.triplePlays.isNotEmpty())
            LabeledStatRow(label = "TP:", entries = stats.fielding.triplePlays)
    }
}