package de.davidbattefeld.berlinskylarks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ui.nav.NavGraph
import ui.nav.NavItemCollection
import ui.nav.NavigationType
import ui.nav.SkylarksNavDestination
import ui.nav.SkylarksTopAppBar

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
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        NavItemCollection(navController, navigationType)
                        Spacer(modifier = Modifier.weight(1.0F))
                        Image(
                            painter = painterResource(R.drawable.logo_rondell),
                            contentDescription = "Skylarks Primary Logo",
                            modifier = Modifier
                                .padding(4.dp)
                                .padding(bottom = 12.dp)
                                .size(60.dp)
                        )
                    }
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
                        drawerContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(250.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 30.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Berlin Skylarks",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier
                                    .padding(bottom = 20.dp)
                                    .align(Alignment.Start)
                            )
                            NavItemCollection(
                                navController = navController,
                                navigationType = navigationType,
                            )
                            Spacer(modifier = Modifier.weight(1.0F))
                            Image(
                                painter = painterResource(R.drawable.logo_rondell),
                                contentDescription = "Skylarks Primary Logo",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(100.dp)
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
                    NavItemCollection(
                        navController = navController,
                        navigationType = navigationType
                    )
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