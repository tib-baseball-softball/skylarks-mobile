package de.davidbattefeld.berlinskylarks.ui.standings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.ui.utility.LoadingView
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import de.davidbattefeld.berlinskylarks.ui.viewmodels.TablesViewModel

@Composable
fun StandingsDetailScreen(
    tableID: Int,
    vm: TablesViewModel
) {
    when (vm.viewState) {
        ViewState.Loading -> {
            LoadingView()
        }

        ViewState.Found -> {
            StandingsTable(table = vm.table.value)
        }

        ViewState.NoResults -> {
            Text(
                text = "The table for this league couldn't be loaded.",
                modifier = Modifier.padding(8.dp)
            )
        }

        else -> {
            Text("Something went very wrong here.", modifier = Modifier.padding(8.dp))
        }
    }

    LaunchedEffect(Unit) {
        // TODO: workaround for view model reuse issue
        //if (vm.viewState != ViewState.Found) {
        vm.loadSingleTable(tableID)
        //}
    }
}