package de.davidbattefeld.berlinskylarks.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.appconfigclient.models.ConfigurationDTO
import de.davidbattefeld.berlinskylarks.data.utility.AndroidDateTimeUtility

@Composable
fun ApplicationConfigCard(
    config: ConfigurationDTO,
    listItemColors: ListItemColors = ListItemDefaults.colors(),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 10.dp),
    ) {
        Text(
            text = "Active Configuration",
            style = MaterialTheme.typography.headlineSmall
        )
        ElevatedCard(
            modifier = Modifier,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            ListItem(
                headlineContent = { Text(text = "Application Context") },
                supportingContent = { Text(config.applicationContext.value.replaceFirstChar { it.uppercase() }) },
                colors = listItemColors
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            ListItem(
                headlineContent = { Text(text = "Name") },
                supportingContent = { Text(config.name) },
                colors = listItemColors
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            ListItem(
                headlineContent = { Text(text = "Last Updated") },
                supportingContent = {
                    Text(
                        AndroidDateTimeUtility.formatDate(
                            config.updatedAt
                        )
                    )
                },
                colors = listItemColors
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            ListItem(
                headlineContent = { Text(text = "Description") },
                supportingContent = { Text(config.description ?: "None") },
                colors = listItemColors
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            ListItem(
                headlineContent = { Text(text = "BSM API URL") },
                supportingContent = { Text(config.apiURLS.bsmURL) },
                colors = listItemColors
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            ListItem(
                headlineContent = { Text(text = "CMS API URL") },
                supportingContent = { Text(config.apiURLS.cmsURL) },
                colors = listItemColors
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            ListItem(
                headlineContent = { Text(text = "DP API URL") },
                supportingContent = { Text(config.apiURLS.dpURL) },
                colors = listItemColors
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
        }
    }
}