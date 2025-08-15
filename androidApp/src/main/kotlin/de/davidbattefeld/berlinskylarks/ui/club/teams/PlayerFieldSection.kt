package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Hexagon
import androidx.compose.material.icons.outlined.SportsBaseball
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
import de.davidbattefeld.berlinskylarks.data.model.Player

@Composable
fun PlayerFieldSection(player: Player) {
    Column {
        Text(
            text = "On the Field",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(start = 12.dp, bottom = 2.dp)
        )
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 1.dp)
        ) {
            Column {
                ListItem(
                    headlineContent = { Text(text = "Field Positions") },
                    supportingContent = { Text(player.positions.joinToString(", ")) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.Hexagon,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                ListItem(
                    headlineContent = { Text(text = "Throws") },
                    supportingContent = { Text(player.throwing) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.SportsBaseball,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                ListItem(
                    headlineContent = { Text(text = "Bats") },
                    supportingContent = { Text(player.batting) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.SportsBaseball,
                            contentDescription = null,
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                )
            }
        }
    }
}