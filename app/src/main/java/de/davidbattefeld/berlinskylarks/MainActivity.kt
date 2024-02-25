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
import ui.nav.SkylarksTopAppBar

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
        topBar = { SkylarksTopAppBar(currentRoute, scrollBehavior) },
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
