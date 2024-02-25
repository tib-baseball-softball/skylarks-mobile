package ui.nav

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
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

        SkylarksNavDestination.ScoresDetail.routeWithArgs -> ScoresTopBar(
            title = SkylarksNavDestination.ScoresDetail.title,
            scrollBehavior = scrollBehavior,
        )

        SkylarksNavDestination.Standings.route -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Standings.title) },
        )

        SkylarksNavDestination.StandingsDetail.routeWithArgs -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.StandingsDetail.title) },
        )

        SkylarksNavDestination.Club.route -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Club.title) },
        )

        SkylarksNavDestination.Teams.route -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Teams.title) },
        )

        SkylarksNavDestination.TeamDetail.routeWithArgs -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.TeamDetail.title) },
        )

        SkylarksNavDestination.PlayerDetail.routeWithArgs -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.PlayerDetail.title) },
        )

        SkylarksNavDestination.Settings.route -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Settings.title) },
        )

        SkylarksNavDestination.Info.route -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Info.title) },
        )

        SkylarksNavDestination.LegalNotice.route -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.LegalNotice.title) },
        )

        SkylarksNavDestination.Privacy.route -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = SkylarksNavDestination.Privacy.title) },
        )

        else -> MediumTopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = "Skylarks") },
        )
    }
}