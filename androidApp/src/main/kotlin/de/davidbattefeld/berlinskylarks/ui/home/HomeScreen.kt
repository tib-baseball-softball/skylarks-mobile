package de.davidbattefeld.berlinskylarks.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import de.davidbattefeld.berlinskylarks.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val selectedTeam by vm.favoriteTeam.collectAsState()
    val teamOptions by vm.teamOptions.collectAsState()

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
        }
    ) { padding ->
        Text(text = "Home", modifier = Modifier.padding(padding))
    }
}