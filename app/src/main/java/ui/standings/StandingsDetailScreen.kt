package ui.standings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.StandingsViewModel
import de.davidbattefeld.berlinskylarks.enums.ViewState

@Composable
fun StandingsDetailScreen(
    tableID: Int,
    vm: StandingsViewModel = viewModel()
) {
    when (vm.viewState.value) {
        ViewState.Loading -> {
            Row(horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
        ViewState.Found -> {
            Text(
                vm.table.value.toString()
            )
        }
        ViewState.NoResults -> {
            Text(text = "The table for this league couldn't be loaded.", modifier = Modifier.padding(8.dp))
        }
        else -> {
            Text("Something went very wrong here.", modifier = Modifier.padding(8.dp))
        }
    }

    LaunchedEffect(Unit) {
        vm.loadSingleTable(tableID)
    }
}