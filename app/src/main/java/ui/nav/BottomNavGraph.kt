package ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.davidbattefeld.berlinskylarks.navigateSingleTopTo
import ui.club.ClubScreen
import ui.home.HomeScreen
import ui.scores.ScoresDetailScreen
import ui.scores.ScoresScreen
import ui.settings.SettingsScreen
import ui.standings.StandingsDetailScreen
import ui.standings.StandingsScreen

@Composable
fun BottomNavGraph(modifier: Modifier, navController: NavHostController) {
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
                ClubScreen()
            }
            composable(route = SkylarksNavDestination.Settings.route) {
                SettingsScreen()
            }
        }
    }
}