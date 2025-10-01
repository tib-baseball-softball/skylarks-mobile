package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.davidbattefeld.berlinskylarks.ui.viewmodels.PlayerDetailViewModel

@Composable
fun PlayerDetailScreen(
    playerID: Int,
    vm: PlayerDetailViewModel
) {
    val player by vm.player.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (player != null) {
            item {
                PlayerHeaderSection(player!!)
            }
            item {
                PlayerMetaSection(player!!)
            }
            item {
                PlayerFieldSection(player!!)
            }
        }
    }
}