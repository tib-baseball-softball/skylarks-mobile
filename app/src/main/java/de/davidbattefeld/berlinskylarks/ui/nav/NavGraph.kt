package de.davidbattefeld.berlinskylarks.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.navigateSingleTopTo
import de.davidbattefeld.berlinskylarks.ui.club.ClubScreen
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

@Composable
fun NavGraph(
    modifier: Modifier,
    navController: NavHostController,
    setFabOnClick: (() -> Unit) -> Unit
) {
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = SkylarksNavDestination.Scores.route // change back to Home later
        ) {
            composable(route = SkylarksNavDestination.Home.route) {
                HomeScreen()
            }

            composable(route = SkylarksNavDestination.Scores.route) {
                ScoresScreen(
                    setFabOnClick = setFabOnClick,
                    detailRoute = { id ->
                        navController.navigateSingleTopTo("${SkylarksNavDestination.ScoresDetail.route}/$id")
                    }
                )
            }
            composable(
                route = SkylarksNavDestination.ScoresDetail.routeWithArgs,
                arguments = SkylarksNavDestination.ScoresDetail.arguments,
            ) {
                val id = it.arguments?.getInt(SkylarksNavDestination.ScoresDetail.scoreArg)
                ScoresDetailScreen(matchID = id ?: BOGUS_ID)
            }

            composable(route = SkylarksNavDestination.Standings.route) {
                StandingsScreen(
                    detailRoute = { id ->
                        navController.navigateSingleTopTo("${SkylarksNavDestination.StandingsDetail.route}/$id")
                    }
                )
            }
            composable(
                route = SkylarksNavDestination.StandingsDetail.routeWithArgs,
                arguments = SkylarksNavDestination.StandingsDetail.arguments
            ) {
                val id = it.arguments?.getInt(SkylarksNavDestination.StandingsDetail.tableArg)
                StandingsDetailScreen(id ?: BOGUS_ID)
            }
            
            composable(route = SkylarksNavDestination.Club.route) {
                ClubScreen(
                    teamsRoute = { navController.navigateSingleTopTo(SkylarksNavDestination.Teams.route) }
                )
            }

            composable(route = SkylarksNavDestination.Teams.route) {
                TeamsScreen(
                    teamsDetailRoute = { id ->
                        navController.navigateSingleTopTo("${SkylarksNavDestination.TeamDetail.route}/$id")
                    }
                )
            }
            composable(
                route = SkylarksNavDestination.TeamDetail.routeWithArgs,
                arguments = SkylarksNavDestination.TeamDetail.arguments,
            ) {
                val id = it.arguments?.getInt(SkylarksNavDestination.TeamDetail.teamArg)
                TeamDetailScreen(
                    teamID = id ?: BOGUS_ID,
                    playerDetailRoute = { playerDetailID ->
                        navController.navigateSingleTopTo("${SkylarksNavDestination.PlayerDetail.route}/$playerDetailID")
                    }
                )
            }
            
            composable(
                route = SkylarksNavDestination.PlayerDetail.routeWithArgs,
                arguments = SkylarksNavDestination.PlayerDetail.arguments,
            ) {
                val id = it.arguments?.getInt(SkylarksNavDestination.PlayerDetail.playerArg)
                PlayerDetailScreen(playerID = id ?: BOGUS_ID)
            }

            composable(route = SkylarksNavDestination.Settings.route) {
                SettingsScreen(
                    infoRoute = { navController.navigateSingleTopTo(SkylarksNavDestination.Info.route) },
                    privacyRoute = { navController.navigateSingleTopTo(SkylarksNavDestination.Privacy.route) },
                    legalRoute = { navController.navigateSingleTopTo(SkylarksNavDestination.LegalNotice.route) },
                )
            }

            composable(SkylarksNavDestination.Info.route) {
                AppInfoScreen()
            }

            composable(SkylarksNavDestination.LegalNotice.route) {
                LegalNoticeScreen()
            }

            composable(SkylarksNavDestination.Privacy.route) {
                PrivacyPolicyScreen()
            }
        }
    }
}