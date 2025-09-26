package de.davidbattefeld.berlinskylarks.ui.scores.gamereport

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScoresViewModel

@Composable
fun ScoresDetailGameReportSection(
    show: Boolean,
    vm: ScoresViewModel = viewModel()
) {
    AnimatedVisibility(
        modifier = Modifier.padding(vertical = 8.dp),
        visible = show,
        enter = expandIn(),
        exit = shrinkOut(),
    ) {
        val gameReport by vm.currentGameReport.collectAsState()
        if (gameReport == null) {
            Text("Game report not available")
        } else {
            Text(gameReport?.reportFirst ?: "still nil")
        }
    }
}