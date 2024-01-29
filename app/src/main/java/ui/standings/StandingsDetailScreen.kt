package ui.standings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.StandingsViewModel

@Composable
fun StandingsDetailScreen(
    tableID: Int,
    vm: StandingsViewModel = viewModel()
) {
    if (vm.table.value.league_id != 9999) {
        Text(
            vm.table.value.toString()
        )
    }

    LaunchedEffect(Unit) {
        vm.loadSingleTable(tableID)
    }
}