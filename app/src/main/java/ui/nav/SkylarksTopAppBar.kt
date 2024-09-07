package ui.nav

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import ui.scores.ScoresTopBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SkylarksTopAppBar(
    currentRoute: State<NavBackStackEntry?>,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    when (currentRoute.value?.destination?.route) {
        SkylarksNavDestination.Scores.route -> ScoresTopBar(
            title = SkylarksNavDestination.Scores.title,
            scrollBehavior = scrollBehavior,
        )

        SkylarksNavDestination.ScoresDetail.routeWithArgs -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.ScoresDetail.title) },
        )

        SkylarksNavDestination.Standings.route -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Standings.title) },
        )

        SkylarksNavDestination.StandingsDetail.routeWithArgs -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.StandingsDetail.title) },
        )

        SkylarksNavDestination.Club.route -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Club.title) },
        )

        SkylarksNavDestination.Teams.route -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Teams.title) },
        )

        SkylarksNavDestination.TeamDetail.routeWithArgs -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.TeamDetail.title) },
        )

        SkylarksNavDestination.PlayerDetail.routeWithArgs -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.PlayerDetail.title) },
        )

        SkylarksNavDestination.Settings.route -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Settings.title) },
        )

        SkylarksNavDestination.Info.route -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Info.title) },
        )

        SkylarksNavDestination.LegalNotice.route -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.LegalNotice.title) },
        )

        SkylarksNavDestination.Privacy.route -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Privacy.title) },
        )

        else -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = "Skylarks") },
        )
    }
}