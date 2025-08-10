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
import de.davidbattefeld.berlinskylarks.BuildConfig

@Composable
fun AppInfoScreen() {
    val listItemColors = ListItemDefaults.colors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    )

    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 1.dp)
            .padding(horizontal = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
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
    }
}