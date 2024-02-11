package ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.davidbattefeld.berlinskylarks.navigateSingleTopTo
import ui.club.ClubScreen
import ui.club.teams.TeamsScreen
import ui.home.HomeScreen
import ui.scores.ScoresDetailScreen
import ui.scores.ScoresScreen
import ui.settings.AppInfoScreen
import ui.settings.LegalNoticeScreen
import ui.settings.PrivacyPolicyScreen
import ui.settings.SettingsScreen
import ui.standings.StandingsDetailScreen
import ui.standings.StandingsScreen

@Composable
fun BottomNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    setFabOnClick: (() -> Unit) -> Unit
) {
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = SkylarksNavDestination.Home.route
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
                ScoresDetailScreen(matchID = id ?: 9999)
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
                StandingsDetailScreen(id ?: 9999)
            }
            
            composable(route = SkylarksNavDestination.Club.route) {
                ClubScreen(
                    teamsRoute = { navController.navigateSingleTopTo(SkylarksNavDestination.Teams.route) }
                )
            }

            composable(route = SkylarksNavDestination.Teams.route) {
                TeamsScreen()
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