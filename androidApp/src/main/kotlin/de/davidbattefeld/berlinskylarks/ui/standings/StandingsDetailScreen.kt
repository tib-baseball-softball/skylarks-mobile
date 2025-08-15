package de.davidbattefeld.berlinskylarks.ui.standings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.StandingsViewModel
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import de.davidbattefeld.berlinskylarks.ui.utility.LoadingView

@Composable
fun StandingsDetailScreen(
    tableID: Int,
    vm: StandingsViewModel = viewModel()
) {
    when (vm.viewState) {
        ViewState.Loading -> {
            LoadingView()
        }
        ViewState.Found -> {
            StandingsTable(table = vm.table.value)
        }
        ViewState.NoResults -> {
            Text(text = "The table for this league couldn't be loaded.", modifier = Modifier.padding(8.dp))
        }
        else -> {
            Text("Something went very wrong here.", modifier = Modifier.padding(8.dp))
        }
    }

    LaunchedEffect(Unit) {
        if (vm.viewState != ViewState.Found) {
            vm.loadSingleTable(tableID)
        }
    }
}