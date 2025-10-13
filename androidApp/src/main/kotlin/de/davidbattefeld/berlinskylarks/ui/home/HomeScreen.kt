package de.davidbattefeld.berlinskylarks.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.LocalSnackbarHostState
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
    val selectedTeam by vm.favoriteTeam.collectAsState()
    val teamOptions by vm.teamOptions.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

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
        Text(text = "Home", modifier = Modifier.padding(padding))
    }
}