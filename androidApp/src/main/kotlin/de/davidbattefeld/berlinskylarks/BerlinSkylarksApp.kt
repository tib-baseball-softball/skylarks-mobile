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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.ui.nav.Home
import de.davidbattefeld.berlinskylarks.ui.nav.NavGraph
import de.davidbattefeld.berlinskylarks.ui.nav.NavItemCollection
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.Scores
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksTopAppBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerlinSkylarksApp(windowSize: WindowWidthSizeClass, ) {
    val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        val topLevelBackStack = remember { TopLevelBackStack<NavKey>(Home) }
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

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

        val snackBarHost = @Composable {
            SnackbarHost(LocalSnackbarHostState.current) { data ->
                Snackbar(
                    modifier = Modifier
                        .padding(15.dp)
                ) {
                    Text(data.visuals.message, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
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
                            NavItemCollection(topLevelBackStack, navigationType)
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
                        topBar = { SkylarksTopAppBar(topLevelBackStack, scrollBehavior) },
                        floatingActionButton = {
                            when (topLevelBackStack.topLevelKey) {
                                Scores -> FloatingActionButton(
                                    onClick = { fabOnClick?.invoke() },
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    content = { Icon(Icons.Filled.Refresh, "refresh games list") },
                                )
                            }
                        },
                        snackbarHost = snackBarHost
                    ) { padding ->
                        NavGraph(
                            topLevelBackStack = topLevelBackStack,
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
                                    topLevelBackStack = topLevelBackStack,
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
                        topBar = { SkylarksTopAppBar(topLevelBackStack, scrollBehavior) },
                        floatingActionButton = {
                            when (topLevelBackStack.topLevelKey) {
                                Scores -> FloatingActionButton(
                                    onClick = { fabOnClick?.invoke() },
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    content = { Icon(Icons.Filled.Refresh, "refresh games list") },
                                )
                            }
                        },
                        snackbarHost = snackBarHost
                    ) { padding ->
                        NavGraph(
                            topLevelBackStack = topLevelBackStack,
                            modifier = Modifier.padding(padding),
                            setFabOnClick = setFabOnClick,
                        )
                    }
                }
            }

            // bottom bar is default, directly into scaffold
            else -> {
                Scaffold(
                    topBar = { SkylarksTopAppBar(topLevelBackStack, scrollBehavior) },
                    floatingActionButton = {
                        when (topLevelBackStack.topLevelKey) {
                            Scores -> FloatingActionButton(
                                onClick = { fabOnClick?.invoke() },
                                containerColor = MaterialTheme.colorScheme.primary,
                                content = { Icon(Icons.Filled.Refresh, "refresh games list") },
                            )
                        }
                    },
                    snackbarHost = snackBarHost,
                    bottomBar = {
                        NavItemCollection(
                            topLevelBackStack = topLevelBackStack,
                            navigationType = navigationType
                        )
                    }
                ) { padding ->
                    NavGraph(
                        topLevelBackStack = topLevelBackStack,
                        modifier = Modifier.padding(padding),
                        setFabOnClick = setFabOnClick,
                    )
                }
            }
        }
    }
}