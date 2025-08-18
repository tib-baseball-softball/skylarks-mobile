package de.davidbattefeld.berlinskylarks.ui.nav

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.ui.scores.ScoresTopBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SkylarksTopAppBar(
    topLevelBackStack: TopLevelBackStack<NavKey>,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val currentRoute = topLevelBackStack.topLevelKey

    when (currentRoute) {
        Scores -> ScoresTopBar(
            scrollBehavior = scrollBehavior,
            title = Scores.title,
        )

        ScoresDetail -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = ScoresDetail.title) },
        )

        Standings -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = Standings.title) },
        )

        StandingsDetail -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = StandingsDetail.title) },
        )

        Club -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = Club.title) },
        )

        Teams -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = Teams.title) },
        )

        TeamDetail -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = TeamDetail.title) },
        )

        PlayerDetail -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = PlayerDetail.title) },
        )

        Functionary -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = Functionary.title) },
        )

        Settings -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = Settings.title) },
        )

        Info -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = Info.title) },
        )

        LegalNotice -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = LegalNotice.title) },
        )

        Privacy -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = Privacy.title) },
        )

        else -> TopAppBar(
            scrollBehavior = scrollBehavior,
            title = { Text(text = "Skylarks") },
        )
    }
}