package de.davidbattefeld.berlinskylarks.ui.standings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val table by vm.table.collectAsState()

    when (vm.viewState) {
        ViewState.Loading -> {
            LoadingView()
        }

        ViewState.Found -> {
            StandingsTable(table = table)
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

    LaunchedEffect(tableID) {
        vm.viewState = ViewState.Found
    }
}