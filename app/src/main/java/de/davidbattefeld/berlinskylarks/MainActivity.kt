package de.davidbattefeld.berlinskylarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import ui.BottomBarScreen
import ui.BottomNavGraph
import ui.NavBar
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

    Scaffold(
        topBar = {
            when (currentRoute.value?.destination?.route) {
                BottomBarScreen.Scores.route -> ScoresTopBar(
                    title = BottomBarScreen.Scores.title,
                    scrollBehavior = scrollBehavior,
                    selectedSeason = 2024, // TODO: get from app state
                )
                else -> MediumTopAppBar(
                    scrollBehavior = scrollBehavior,
                    title = { Text(text = "Skylarks") },
                )
            }
        },
        bottomBar = { NavBar(navController = navController) }
    ) { padding ->
        BottomNavGraph(navController = navController, modifier = Modifier.padding(padding))
    }
}