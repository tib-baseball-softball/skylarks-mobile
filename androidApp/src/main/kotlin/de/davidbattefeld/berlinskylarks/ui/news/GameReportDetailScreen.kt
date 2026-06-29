package de.davidbattefeld.berlinskylarks.ui.news

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost

@Composable
fun GameReportDetailScreen(
    vm: GameReportDetailViewModel,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val gameReport by vm.gameReport.collectAsState()
    val game1 = vm.games.firstOrNull()?.collectAsState()
    val game2 = vm.games.lastOrNull()?.collectAsState()

    Scaffold(
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = {
            SkylarksBottomBar(
                topLevelBackStack,
                navigationType
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            item {
                if (gameReport == null) {
                    ContentNotFoundView("game report")
                } else {
                    GameReportDetailContent(gameReport = gameReport!!, game1?.value, game2?.value)
                }
            }
        }
    }
}