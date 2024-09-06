package de.davidbattefeld.berlinskylarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.davidbattefeld.berlinskylarks.ui.theme.BerlinSkylarksTheme
import ui.nav.NavBar
import ui.nav.NavGraph
import ui.nav.NavigationType
import ui.nav.SkylarksNavDestination
import ui.nav.SkylarksTopAppBar

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BerlinSkylarksTheme {
                val windowSize = calculateWindowSizeClass(this)
                navController = rememberNavController()

                BerlinSkylarksApp(
                    navController = navController,
                    windowSize = windowSize.widthSizeClass,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerlinSkylarksApp(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
) {
    val currentRoute =
        navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }

    val navigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            NavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            NavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            NavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            NavigationType.BOTTOM_NAVIGATION
        }
    }


    when (navigationType) {
        NavigationType.NAVIGATION_RAIL -> {
            Row {
                NavigationRail {
                    //TODO
                    NavigationRailItem(
                        icon = { Icon(Icons.Filled.BrokenImage, contentDescription = null) },
                        label = { Text("MÖP") },
                        selected = false,
                        onClick = {  },
                    )

                }
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
                ) { padding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(padding),
                        setFabOnClick = setFabOnClick,
                    )
                }
            }
        }

        NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(250.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            // TODO
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Filled.BrokenImage, contentDescription = null) },
                                label = { Text("MÖP") },
                                selected = false,
                                onClick = {  },
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )

                        }
                    }
                }
            ) {
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
                ) { padding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(padding),
                        setFabOnClick = setFabOnClick,
                    )
                }
            }
        }

        // bottom bar is default, directly into scaffold
        else -> {
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
                bottomBar = {
                    NavBar(navController = navController)
                }
            ) { padding ->
                NavGraph(
                    navController = navController,
                    modifier = Modifier.padding(padding),
                    setFabOnClick = setFabOnClick,
                )
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    launchSingleTop = true
}
