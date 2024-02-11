package de.davidbattefeld.berlinskylarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import ui.nav.BottomNavGraph
import ui.nav.NavBar
import ui.nav.SkylarksNavDestination
import ui.scores.ScoresTopBar

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BerlinSkylarksTheme {
                navController = rememberNavController()
                BerlinSkylarksApp(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerlinSkylarksApp(navController: NavHostController) {
    val currentRoute = navController
        .currentBackStackEntryFlow
        .collectAsState(initial = navController.currentBackStackEntry)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }

    Scaffold(
        topBar = {
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
        },
        floatingActionButton = {
            when (currentRoute.value?.destination?.route) {
                SkylarksNavDestination.Scores.route -> ExtendedFloatingActionButton(
                    onClick = { fabOnClick?.invoke() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    expanded = true,
                    icon = { Icon(Icons.Filled.Refresh, "load new") },
                    text = { Text(text = "Load Games") },
                )
            }
        },
        bottomBar = { NavBar(navController = navController) }
    ) { padding ->
        BottomNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding),
            setFabOnClick = setFabOnClick,
        )
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }
