package ui.club.teams

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.PlayersViewModel

@Composable
fun PlayerDetailScreen(playerID: Int) {
    val vm: PlayersViewModel = viewModel(LocalContext.current as ComponentActivity)
    val player = vm.getFiltered(playerID, vm.players)

    Text(text = player.toString())
}