package de.davidbattefeld.berlinskylarks.ui.club.licenses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.Scorers
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScorersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScorersScreen(
    vm: ScorersViewModel,
    detailRoute: (Int) -> Unit,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val licenses by vm.licenses.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = Scorers.title) },
            )
        },
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = { SkylarksBottomBar(topLevelBackStack, navigationType) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            licenses.forEach { license ->
                val name = "${'$'}{license.person.firstName} ${'$'}{license.person.lastName}"
                Text(
                    text = "$name - ${'$'}{license.level}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { detailRoute(license.id.toInt()) }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
    }
}