package de.davidbattefeld.berlinskylarks.ui.scores

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScoresViewModel

@Composable
fun ScoresDetailBoxScoreSection(
    show: Boolean,
    vm: ScoresViewModel = viewModel()
) {
    AnimatedVisibility(
        modifier = Modifier.padding(vertical = 8.dp),
        visible = show,
        enter = expandIn(),
        exit = shrinkOut(),
    ) {
        Text("Box Score is ${vm.currentBoxScore?.fullBoxScoreHTML ?: "not available"}")
    }
}