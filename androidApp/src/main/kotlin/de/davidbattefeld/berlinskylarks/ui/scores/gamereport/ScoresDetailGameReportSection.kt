package de.davidbattefeld.berlinskylarks.ui.scores.gamereport

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScoresDetailGameReportSection(
    show: Boolean,
) {
    AnimatedVisibility(
        modifier = Modifier.padding(vertical = 8.dp),
        visible = show,
        enter = expandIn(),
        exit = shrinkOut(),
    ) {
        Text("Game Report")
    }
}