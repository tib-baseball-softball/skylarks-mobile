package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.davidbattefeld.berlinskylarks.classes.viewmodels.PlayersViewModel

@Composable
fun PlayerDetailScreen(playerID: Int) {
    val vm: PlayersViewModel = viewModel(LocalContext.current as ComponentActivity)
    val player = vm.getFiltered(playerID, vm.players)

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (player != null) {
            item {
                PlayerHeaderSection(player)
            }
            item {
                PlayerMetaSection(player)
            }
            item {
                PlayerFieldSection(player)
            }
        }
    }
}