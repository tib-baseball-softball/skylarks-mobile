package ui.club.teams

import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.TeamsViewModel

@Composable
fun TeamsScreen() {
    val vm: TeamsViewModel = viewModel(LocalContext.current as ComponentActivity)
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState
    ) {
        items(vm.teams) {
            Text(it.toString())
        }
    }
    LaunchedEffect(Unit) {
        if (vm.teams.isEmpty()) {
            vm.load()
        }
    }
}