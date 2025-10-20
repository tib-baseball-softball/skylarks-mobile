package de.davidbattefeld.berlinskylarks.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import de.berlinskylarks.shared.data.model.tib.SkylarksTeam
import de.davidbattefeld.berlinskylarks.LocalSnackbarHostState
import de.davidbattefeld.berlinskylarks.ui.club.teams.TeamInfoCard
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import de.davidbattefeld.berlinskylarks.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current
    val scrollState = rememberScrollState()

    val selectedTeam by vm.favoriteTeam.collectAsState()
    val teamOptions by vm.teamOptions.collectAsState()
    val homeDatasets by vm.homeDatasets.collectAsState()
    val favoriteTeam by vm.favoriteTeam.collectAsState()

    Scaffold(
        topBar = {
            HomeTopBar(
                scrollBehavior = scrollBehavior,
                bsmTeams = teamOptions,
                onTeamFilterChanged = vm::onTeamFilterChanged,
                selectedTeam = selectedTeam,
            )
        },
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = {
            SkylarksBottomBar(topLevelBackStack, navigationType)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.loadHomeData()
                    scope.launch {
                        snackbarHostState.showSnackbar(message = "Refreshing data - this can take a while.")
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                content = { Icon(Icons.Filled.Refresh, "refresh home data") },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TeamInfoCard(
                team = SkylarksTeam(
                    id = favoriteTeam?.id ?: 0,
                    name = favoriteTeam?.name ?: "",
                    leagueID = favoriteTeam?.leagueEntries?.firstOrNull()?.league?.id ?: 0,
                    bsmShortName = favoriteTeam?.shortName,
                    sport = favoriteTeam?.leagueEntries?.firstOrNull()?.league?.sport ?: "",
                    ageGroup = favoriteTeam?.leagueEntries?.firstOrNull()?.league?.ageGroup ?: "",
                ),
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            )

            Text(text = "Data per League", style = MaterialTheme.typography.headlineSmall)

            homeDatasets.forEach {
                HomeDatasetItem(dataset = it)
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            }
        }
    }
}