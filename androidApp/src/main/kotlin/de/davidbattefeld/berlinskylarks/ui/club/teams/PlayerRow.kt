package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.berlinskylarks.shared.data.model.Player

@Composable
fun PlayerRow(
    player: Player,
    modifier: Modifier,
) {
    val colors = when (player.isCoach()) {
        true -> Pair(MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.onSecondary)
        false -> Pair(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
    }

    ListItem(
        headlineContent = { Text("${player.lastName}, ${player.firstName}") },
        supportingContent = { Text(player.positions.joinToString(", ")) },
        leadingContent = {
            PlayerNumberGraphic(content = player.number, colors)
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "click to show more info",
            )
        },
        modifier = modifier,
    )
}