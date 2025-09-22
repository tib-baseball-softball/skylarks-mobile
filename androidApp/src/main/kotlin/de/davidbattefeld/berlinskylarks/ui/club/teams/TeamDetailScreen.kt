package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.berlinskylarks.shared.data.model.Player
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.utility.LoadingView
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import de.davidbattefeld.berlinskylarks.ui.viewmodels.AppViewModelProvider
import de.davidbattefeld.berlinskylarks.ui.viewmodels.PlayersViewModel

@Composable
fun TeamDetailScreen(
    teamID: Int,
    playerDetailRoute: (Int) -> Unit,
    vm: PlayersViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val listState = rememberLazyListState()

    Surface(
        shape = RoundedCornerShape(size = 12.dp),
        tonalElevation = 1.dp,
        modifier = Modifier
            .padding(8.dp)
    ) {
        LazyColumn(
            state = listState
        ) {
            when (vm.viewState) {
                ViewState.NoResults, ViewState.NotInitialised -> {
                    item {
                        ContentNotFoundView("players")
                    }
                }

                ViewState.Loading -> {
                    item {
                        LoadingView()
                    }
                }

                ViewState.Found -> {
                    itemsIndexed<Player>(vm.players) { index, player ->
                        Column {
                            PlayerRow(
                                player = player,
                                modifier = Modifier
                                    .clickable {
                                        playerDetailRoute(player.id)
                                    }
                            )
                            // last item does not have a divider
                            if (index + 1 != vm.players.size) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }
                }

                ViewState.Error -> {
                    item {
                        Text("An error occured loading data.")
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (vm.players.isEmpty() || vm.lastLoadedTeam != teamID) {
            vm.loadPlayers(teamID)
        }
    }
}