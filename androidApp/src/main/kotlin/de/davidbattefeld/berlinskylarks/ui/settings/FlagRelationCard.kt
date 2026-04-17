package de.davidbattefeld.berlinskylarks.ui.settings

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import de.berlinskylarks.appconfigclient.models.FlagWithStatusDTO

@Composable
fun FlagRelationCard(
    relations: Map<String, FlagWithStatusDTO>,
    listItemColors: ListItemColors = ListItemDefaults.colors(),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 20.dp),
    ) {
        Text(
            text = "Feature Flags",
            style = MaterialTheme.typography.headlineSmall
        )
        ElevatedCard(
            modifier = Modifier,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            relations.forEach {
                ListItem(
                    headlineContent = {
                        Text(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                                    shape = MaterialTheme.shapes.small
                                )
                                .padding(4.dp),
                            color = MaterialTheme.colorScheme.primary,
                            text = it.key,
                            fontFamily = FontFamily.Monospace,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    supportingContent = { Text(if (it.value.enabled) "Enabled" else "Disabled") },
                    colors = listItemColors
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            }
            if (relations.isEmpty()) {
                Text(text = "No relations found")
            }
        }
    }
}