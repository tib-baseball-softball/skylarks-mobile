package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Tag
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.berlinskylarks.shared.data.model.Player

@Composable
fun PlayerMetaSection(player: Player) {
    Column {
        Text(
            text = "Player Data",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(start = 12.dp, bottom = 2.dp)
        )
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 1.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
        ) {
            Column {
                ListItem(
                    headlineContent = { Text(text = "Team") },
                    supportingContent = { Text(player.teams.joinToString { it.name }) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Groups,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                ListItem(
                    headlineContent = { Text(text = "Age") },
                    supportingContent = { Text(player.getAge().toString()) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Tag,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                ListItem(
                    headlineContent = { Text(text = "Member Since") },
                    supportingContent = { Text(player.admission) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Event,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
            }
        }
    }
}