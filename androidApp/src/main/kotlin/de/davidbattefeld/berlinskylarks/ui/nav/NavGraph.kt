package de.davidbattefeld.berlinskylarks.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import de.davidbattefeld.berlinskylarks.ui.club.ClubDetailScreen
import de.davidbattefeld.berlinskylarks.ui.club.ClubScreen
import de.davidbattefeld.berlinskylarks.ui.club.field.FieldDetailScreen
import de.davidbattefeld.berlinskylarks.ui.club.field.FieldsScreen
import de.davidbattefeld.berlinskylarks.ui.club.functionary.FunctionaryDetailScreen
import de.davidbattefeld.berlinskylarks.ui.club.functionary.FunctionaryScreen
import de.davidbattefeld.berlinskylarks.ui.club.licenses.LicenseDetailScreen
import de.davidbattefeld.berlinskylarks.ui.club.licenses.ScorersScreen
import de.davidbattefeld.berlinskylarks.ui.club.licenses.UmpiresScreen
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
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ClubDetailViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ClubViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.FieldDetailViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.FieldsViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.FunctionaryDetailViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.FunctionaryViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.HomeViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.LeagueGroupsViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.LegalNoticeViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.LicenseDetailViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.PlayerDetailViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.PrivacyPolicyViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScoreDetailViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScorersViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.ScoresViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.SettingsViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.TablesViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.TeamDetailViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.TeamsViewModel
import de.davidbattefeld.berlinskylarks.ui.viewmodels.UmpiresViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    modifier: Modifier,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType
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
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                val vm = hiltViewModel<HomeViewModel, HomeViewModel.Factory>(
                    creationCallback = { factory ->
                        factory.create()
                    }
                )
                entry<Home> {
                    HomeScreen(
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType
                    )
                }

                entry<Scores>(
                    metadata = ListDetailSceneStrategy.listPane(
                        detailPlaceholder = {
                            Text("Choose a game from the list")
                        }
                    )
                ) {
                    val vm = hiltViewModel<ScoresViewModel, ScoresViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    ScoresScreen(
                        detailRoute = { key ->
                            topLevelBackStack.add(key)
                        },
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }
                entry<ScoresDetail>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) { key ->
                    val vm = hiltViewModel<ScoreDetailViewModel, ScoreDetailViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(key)
                        }
                    )
                    ScoresDetailScreen(
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<Standings> {
                    val vm = hiltViewModel<LeagueGroupsViewModel, LeagueGroupsViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    StandingsScreen(
                        detailRoute = { id ->
                            topLevelBackStack.add(StandingsDetail(id))
                        },
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<StandingsDetail> { key ->
                    val vm = hiltViewModel<TablesViewModel, TablesViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(key)
                        }
                    )
                    StandingsDetailScreen(
                        tableID = key.id,
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<Club> { key ->
                    val vm = hiltViewModel<ClubViewModel, ClubViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(key)
                        }
                    )
                    ClubScreen(
                        vm = vm,
                        teamsRoute = { topLevelBackStack.add(Teams) },
                        functionaryRoute = { topLevelBackStack.add(Functionary) },
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                        clubDetailRoute = { id -> topLevelBackStack.add(ClubDetail(id)) },
                        umpireRoute = { topLevelBackStack.add(Umpires) },
                        scorersRoute = { topLevelBackStack.add(Scorers) },
                        fieldsRoute = { topLevelBackStack.add(Field) },
                    )
                }

                entry<Teams> {
                    val vm = hiltViewModel<TeamsViewModel, TeamsViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    TeamsScreen(
                        teamsDetailRoute = { id ->
                            topLevelBackStack.add(TeamDetail(id))
                        },
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }
                entry<TeamDetail> { key ->
                    val vm = hiltViewModel<TeamDetailViewModel, TeamDetailViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(key)
                        }
                    )
                    TeamDetailScreen(
                        teamID = key.id,
                        playerDetailRoute = { playerDetailID ->
                            topLevelBackStack.add(PlayerDetail(playerDetailID))
                        },
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<PlayerDetail> { key ->
                    val vm = hiltViewModel<PlayerDetailViewModel, PlayerDetailViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(key)
                        }
                    )
                    PlayerDetailScreen(
                        playerID = key.id,
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<Functionary> {
                    val vm = hiltViewModel<FunctionaryViewModel, FunctionaryViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    FunctionaryScreen(
                        vm = vm,
                        detailRoute = {
                            topLevelBackStack.add(FunctionaryDetail(it))
                        },
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<FunctionaryDetail> {
                    val vm =
                        hiltViewModel<FunctionaryDetailViewModel, FunctionaryDetailViewModel.Factory>(
                            creationCallback = { factory ->
                                factory.create(it)
                            }
                        )
                    FunctionaryDetailScreen(
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<ClubDetail> { key ->
                    val vm = hiltViewModel<ClubDetailViewModel, ClubDetailViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(key)
                        }
                    )
                    ClubDetailScreen(
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<Umpires> {
                    val vm = hiltViewModel<UmpiresViewModel, UmpiresViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    UmpiresScreen(
                        vm = vm,
                        detailRoute = { id ->
                            topLevelBackStack.add(LicenseDetail(id))
                        },
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<Scorers> {
                    val vm = hiltViewModel<ScorersViewModel, ScorersViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    ScorersScreen(
                        vm = vm,
                        detailRoute = { id ->
                            topLevelBackStack.add(LicenseDetail(id))
                        },
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<LicenseDetail> { key ->
                    val vm = hiltViewModel<LicenseDetailViewModel, LicenseDetailViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(key)
                        }
                    )
                    LicenseDetailScreen(
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<Field> {
                    val vm = hiltViewModel<FieldsViewModel, FieldsViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    FieldsScreen(
                        vm = vm,
                        detailRoute = { id ->
                            topLevelBackStack.add(FieldDetail(id))
                        },
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<FieldDetail> { key ->
                    val vm = hiltViewModel<FieldDetailViewModel, FieldDetailViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create(key)
                        }
                    )
                    FieldDetailScreen(
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<Settings> {
                    val vm = hiltViewModel<SettingsViewModel, SettingsViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    SettingsScreen(
                        infoRoute = { topLevelBackStack.add(Info) },
                        privacyRoute = { topLevelBackStack.add(Privacy) },
                        legalRoute = { topLevelBackStack.add(LegalNotice) },
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<Info> {
                    AppInfoScreen(
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<LegalNotice> {
                    val vm = hiltViewModel<LegalNoticeViewModel, LegalNoticeViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    LegalNoticeScreen(
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }

                entry<Privacy> {
                    val vm = hiltViewModel<PrivacyPolicyViewModel, PrivacyPolicyViewModel.Factory>(
                        creationCallback = { factory ->
                            factory.create()
                        }
                    )
                    PrivacyPolicyScreen(
                        vm = vm,
                        topLevelBackStack = topLevelBackStack,
                        navigationType = navigationType,
                    )
                }
            }
        )
    }
}