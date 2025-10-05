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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import de.davidbattefeld.berlinskylarks.ui.nav.Home
import de.davidbattefeld.berlinskylarks.ui.nav.NavGraph
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksSidebarNavigation
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerlinSkylarksApp(windowSize: WindowWidthSizeClass) {
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        val topLevelBackStack = remember { TopLevelBackStack<NavKey>(Home) }

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
                            SkylarksSidebarNavigation(topLevelBackStack, navigationType)
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
                    NavGraph(
                        topLevelBackStack = topLevelBackStack,
                        modifier = Modifier,
                        navigationType = navigationType
                    )
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
                                SkylarksSidebarNavigation(
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
                    NavGraph(
                        topLevelBackStack = topLevelBackStack,
                        modifier = Modifier,
                        navigationType = navigationType
                    )
                }
            }

            else -> {
                NavGraph(
                    topLevelBackStack = topLevelBackStack,
                    modifier = Modifier,
                    navigationType = navigationType
                )
            }
        }
    }
}