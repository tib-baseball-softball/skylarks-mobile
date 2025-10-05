package de.davidbattefeld.berlinskylarks.ui.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.Greeting
import de.davidbattefeld.berlinskylarks.BuildConfig

@androidx.compose.material3.ExperimentalMaterial3Api
@Composable
fun AppInfoScreen(
    topLevelBackStack: de.davidbattefeld.berlinskylarks.ui.nav.TopLevelBackStack<androidx.navigation3.runtime.NavKey>,
    navigationType: de.davidbattefeld.berlinskylarks.ui.nav.NavigationType,
) {
    val listItemColors = ListItemDefaults.colors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        headlineColor = MaterialTheme.colorScheme.onSurface,
        supportingColor = MaterialTheme.colorScheme.onSurface,
    )

    val scrollBehavior =
        androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    androidx.compose.material3.Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = de.davidbattefeld.berlinskylarks.ui.nav.Info.title) },
            )
        },
        snackbarHost = { de.davidbattefeld.berlinskylarks.ui.utility.SkylarksSnackbarHost() },
        bottomBar = {
            de.davidbattefeld.berlinskylarks.ui.nav.SkylarksBottomBar(
                topLevelBackStack,
                navigationType
            )
        }
    ) { paddingValues ->

        ElevatedCard(
            modifier = Modifier
                .padding(paddingValues)
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
}