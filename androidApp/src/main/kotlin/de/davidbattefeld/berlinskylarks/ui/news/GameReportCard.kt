package de.davidbattefeld.berlinskylarks.ui.news

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.berlinskylarks.shared.data.model.tib.GameReport

@Composable
fun GameReportCard(
    modifier: Modifier = Modifier,
    gameReport: GameReport,
) {
    Text(gameReport.title, modifier = modifier)
}