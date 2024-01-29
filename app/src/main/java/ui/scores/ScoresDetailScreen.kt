package ui.scores

import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.ScoresViewModel

@Composable
fun ScoresDetailScreen(
    matchID: Int
) {
    val vm: ScoresViewModel = viewModel(LocalContext.current as ComponentActivity)

    Text(vm.getFilteredGame(matchID).toString())
}