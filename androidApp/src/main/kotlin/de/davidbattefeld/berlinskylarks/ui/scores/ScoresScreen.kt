package de.davidbattefeld.berlinskylarks.ui.scores

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import de.berlinskylarks.shared.data.enums.Gameday
import de.davidbattefeld.berlinskylarks.LocalSnackbarHostState
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.ScoresDetail
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import de.davidbattefeld.berlinskylarks.ui.utility.ContentNotFoundView
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScoresViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScoresScreen(
    modifier: Modifier = Modifier,
    detailRoute: (ScoresDetail) -> Unit,
    vm: ScoresViewModel,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val games by vm.games.collectAsStateWithLifecycle()
    val leagueGroups by vm.leagueGroups.collectAsStateWithLifecycle()
    val filteredLeagueGroup by vm.filteredLeagueGroup.collectAsStateWithLifecycle()
    val showExternalGames by vm.showExternalGames.collectAsStateWithLifecycle()
    val selectedGameday by vm.selectedGameday.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            ScoresTopBar(
                scrollBehavior = scrollBehavior,
                leagueGroups = leagueGroups,
                filteredLeagueGroup = filteredLeagueGroup,
                games = games,
                onLeagueFilterChanged = vm::onLeagueFilterChanged,
                addGamesFunc = vm::addGamesToCalendar,
                loadCalendarFunc = vm::loadCalendars,
            )
        },
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = { SkylarksBottomBar(topLevelBackStack, navigationType) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.refresh()
                    scope.launch {
                        snackbarHostState.showSnackbar(message = "Refreshing games - this can take a while.")
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                content = { Icon(Icons.Filled.Refresh, "refresh games list") },
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 6.dp),
            columns = GridCells.Adaptive(minSize = 350.dp)
        ) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                val options = Gameday.entries.filterNot { it == Gameday.NEXT }
                FlowRow(
                    Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    options.forEachIndexed { index, gamedayOption ->
                        ToggleButton(
                            checked = selectedGameday == gamedayOption,
                            onCheckedChange = { vm.onGamedayChanged(gamedayOption) },
                            shapes =
                                when (index) {
                                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                    options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                },
                            modifier = Modifier
                                .semantics { role = Role.RadioButton }
                                .weight(0.25F)
                            ,
                        ) {
                            Text(
                                text = gamedayOption.value.replaceFirstChar { it.uppercase() },
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Row(
                    modifier = Modifier
                        .padding(14.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8F)
                    ) {
                        Text(
                            text = "Show External Games",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Controls whether the list is filtered by just Skylarks games.",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1.0F))
                    Switch(
                        modifier = Modifier.semantics {
                            contentDescription = "Show external Games"
                        },
                        checked = showExternalGames,
                        onCheckedChange = { vm.onShowExternalGamesChanged(it) },
                        colors = SwitchDefaults.colors()
                    )
                }
            }
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            }
            if (games.isEmpty()) {
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    ContentNotFoundView("games")
                }
            } else {
                items(games) { gameDecorator ->
                    ScoresItem(
                        gameDecorator = gameDecorator,
                        modifier = Modifier
                            .clickable {
                                detailRoute(
                                    ScoresDetail(
                                        id = gameDecorator.game.id.toInt(),
                                        matchID = gameDecorator.game.matchID
                                    )
                                )
                            }
                    )
                }
            }

            //        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            //            LoadingView()
            //        }

            //        item {
            //            Text("An error occured loading data.")
            //        }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 400,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(
    showBackground = true,
    widthDp = 400
)
@Composable
fun ScoresScreenPreview() {
    BerlinSkylarksTheme {
        // ScoresScreen()
    }
}