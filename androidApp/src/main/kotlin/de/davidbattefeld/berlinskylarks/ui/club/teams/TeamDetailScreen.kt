package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TeamDetail
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.utility.LoadingView
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import de.davidbattefeld.berlinskylarks.ui.viewmodels.TeamDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreen(
    playerDetailRoute: (Int) -> Unit,
    vm: TeamDetailViewModel,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val listState = rememberLazyListState()
    val players by vm.players.collectAsState()
    val team by vm.team.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = TeamDetail.title) },
            )
        },
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = { SkylarksBottomBar(topLevelBackStack, navigationType) }
    ) { paddingValues ->
        Surface(
            shape = RoundedCornerShape(size = 12.dp),
            tonalElevation = 1.dp,
            modifier = Modifier
                .padding(paddingValues)
                .padding(12.dp),
        ) {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
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
                        team?.let {
                            item {
                                TeamInfoCard(
                                    team = team!!,
                                    backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
                                )
                            }
                        }

                        itemsIndexed(players) { index, player ->
                            PlayerRow(
                                player = player,
                                modifier = Modifier
                                    .clickable {
                                        playerDetailRoute(player.id.toInt())
                                    }
                            )
                            // last item does not have a divider
                            if (index + 1 != players.size) {
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }

                    ViewState.Error -> {
                        item {
                            Text("An error occurred loading data.")
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.viewState = ViewState.Found
    }
}