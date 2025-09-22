package de.davidbattefeld.berlinskylarks.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import de.davidbattefeld.berlinskylarks.ui.club.ClubScreen
import de.davidbattefeld.berlinskylarks.ui.club.functionary.FunctionaryScreen
import de.davidbattefeld.berlinskylarks.ui.club.teams.PlayerDetailScreen
import de.davidbattefeld.berlinskylarks.ui.club.teams.TeamDetailScreen
import de.davidbattefeld.berlinskylarks.ui.club.teams.TeamsScreen
import de.davidbattefeld.berlinskylarks.ui.home.HomeScreen
import de.davidbattefeld.berlinskylarks.ui.scores.ScoresDetailScreen
import de.davidbattefeld.berlinskylarks.ui.scores.ScoresScreen
import de.davidbattefeld.berlinskylarks.ui.settings.AppInfoScreen
import de.davidbattefeld.berlinskylarks.ui.settings.LegalNoticeScreen
import de.davidbattefeld.berlinskylarks.ui.settings.PrivacyPolicyScreen
import de.davidbattefeld.berlinskylarks.ui.settings.SettingsScreen
import de.davidbattefeld.berlinskylarks.ui.standings.StandingsDetailScreen
import de.davidbattefeld.berlinskylarks.ui.standings.StandingsScreen
import de.davidbattefeld.berlinskylarks.ui.viewmodels.LeagueGroupsViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.TablesViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavGraph(
    modifier: Modifier,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    setFabOnClick: (() -> Unit) -> Unit
) {
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    Box(modifier = modifier) {
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            sceneStrategy = listDetailStrategy,
            entryDecorators = listOf(
                rememberSceneSetupNavEntryDecorator(),
                rememberSavedStateNavEntryDecorator(),
                // TODO: this line crashes app (application not populated)
                //rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Home> {
                    HomeScreen()
                }

                entry<Scores>(
                    metadata = ListDetailSceneStrategy.listPane(
                        detailPlaceholder = {
                            Text("Choose a game from the list")
                        }
                    )
                ) {
                    ScoresScreen(
                        setFabOnClick = setFabOnClick,
                        detailRoute = { id ->
                            topLevelBackStack.add(ScoresDetail(id))
                        }
                    )
                }
                entry<ScoresDetail>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) { key ->
                    ScoresDetailScreen(matchID = key.id)
                }

                entry<Standings> {
                    StandingsScreen(
                        detailRoute = { id ->
                            topLevelBackStack.add(StandingsDetail(id))
                        },
                        vm = viewModel(factory = LeagueGroupsViewModel.Factory)
                    )
                }

                entry<StandingsDetail> { key ->
                    StandingsDetailScreen(
                        tableID = key.id,
                        vm = viewModel(factory = TablesViewModel.Factory(key))
                    )
                }

                entry<Club> {
                    ClubScreen(
                        teamsRoute = { topLevelBackStack.add(Teams) },
                        functionaryRoute = { topLevelBackStack.add(Functionary) },
                    )
                }

                entry<Teams> {
                    TeamsScreen(
                        teamsDetailRoute = { id ->
                            topLevelBackStack.add(TeamDetail(id))
                        }
                    )
                }
                entry<TeamDetail> { key ->
                    TeamDetailScreen(
                        teamID = key.id,
                        playerDetailRoute = { playerDetailID ->
                            topLevelBackStack.add(PlayerDetail(playerDetailID))
                        }
                    )
                }

                entry<PlayerDetail> { key ->
                    PlayerDetailScreen(playerID = key.id)
                }

                entry<Functionary> {
                    FunctionaryScreen()
                }

                entry<Settings> {
                    SettingsScreen(
                        infoRoute = { topLevelBackStack.add(Info) },
                        privacyRoute = { topLevelBackStack.add(Privacy) },
                        legalRoute = { topLevelBackStack.add(LegalNotice) },
                    )
                }

                entry<Info> {
                    AppInfoScreen()
                }

                entry<LegalNotice> {
                    LegalNoticeScreen()
                }

                entry<Privacy> {
                    PrivacyPolicyScreen()
                }
            }
        )
    }
}