package de.davidbattefeld.berlinskylarks.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import de.berlinskylarks.shared.Greeting
import de.davidbattefeld.berlinskylarks.BuildConfig
import de.davidbattefeld.berlinskylarks.ui.nav.Info
import de.davidbattefeld.berlinskylarks.ui.nav.NavigationType
import de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar
import de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack
import de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost
import de.davidbattefeld.berlinskylarks.ui.viewmodels.AppInfoViewModel

@androidx.compose.material3.ExperimentalMaterial3Api
@Composable
fun AppInfoScreen(
    vm: AppInfoViewModel,
    topLevelBackStack: TopLevelBackStack<NavKey>,
    navigationType: NavigationType,
) {
    val listItemColors = ListItemDefaults.colors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        headlineColor = MaterialTheme.colorScheme.onSurface,
        supportingColor = MaterialTheme.colorScheme.onSurface,
    )

    val listState = rememberLazyListState()

    val activeConfig by vm.activeConfig.collectAsStateWithLifecycle(initialValue = null)

    val scrollBehavior =
        androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = Info.title) },
            )
        },
        snackbarHost = { SkylarksSnackbarHost() },
        bottomBar = {
            SkylarksBottomBar(
                topLevelBackStack,
                navigationType
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { vm.syncConfigs() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Sync Configurations"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                ElevatedCard(
                    modifier = Modifier
                        .padding(bottom = 1.dp)
                        .padding(horizontal = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                ) {
                    ListItem(
                        headlineContent = { Text(text = "App ID") },
                        supportingContent = { Text(BuildConfig.APPLICATION_ID) },
                        colors = listItemColors
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                    ListItem(
                        headlineContent = { Text(text = "Version") },
                        supportingContent = { Text(BuildConfig.VERSION_NAME) },
                        colors = listItemColors
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                    ListItem(
                        headlineContent = { Text(text = "Build Number") },
                        supportingContent = { Text(BuildConfig.VERSION_CODE.toString()) },
                        colors = listItemColors
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                    ListItem(
                        headlineContent = { Text(text = "Build Type") },
                        supportingContent = { Text(BuildConfig.BUILD_TYPE) },
                        colors = listItemColors
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                    ListItem(
                        headlineContent = { Text(text = "Kotlin Multiplatform") },
                        supportingContent = { Text(Greeting().greet()) },
                        colors = listItemColors
                    )
                }
            }

            item {
                if (activeConfig != null) {
                    activeConfig?.let { config ->
                        ApplicationConfigCard(
                            config = config,
                            listItemColors = listItemColors
                        )
                    }
                } else {
                    LoadingIndicator()
                }
            }

            item {
                activeConfig?.flagRelations?.let { relations ->
                    FlagRelationCard(
                        relations = relations,
                        listItemColors = listItemColors
                    )
                }
            }
        }
    }
}
