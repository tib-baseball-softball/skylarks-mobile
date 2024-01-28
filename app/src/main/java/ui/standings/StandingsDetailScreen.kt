package ui.standings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.StandingsViewModel

@Composable
fun StandingsDetailScreen(
    tableID: Int,
    standingsViewModel: StandingsViewModel = viewModel()
) {
    Text(
        tableID.toString()
        //standingsViewModel.tables.filter { it.league_id == tableID }
    )
}