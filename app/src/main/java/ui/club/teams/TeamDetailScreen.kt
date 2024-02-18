package ui.club.teams

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.TeamsViewModel

@Composable
fun TeamDetailScreen(teamID: Int) {
    val vm: TeamsViewModel = viewModel(LocalContext.current as ComponentActivity)

    Text(text = vm.players.toList().toString())

    LaunchedEffect(Unit) {
        vm.loadPlayers(teamID)
    }
}