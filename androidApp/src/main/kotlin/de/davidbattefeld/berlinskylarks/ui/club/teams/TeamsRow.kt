package de.davidbattefeld.berlinskylarks.ui.club.teams

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.berlinskylarks.shared.data.model.SkylarksTeam

@Composable
fun TeamsRow(
    team: SkylarksTeam,
    modifier: Modifier,
) {
    ListItem(
        headlineContent = { Text(team.name) },
        supportingContent = { Text(team.bsmShortName) },
        leadingContent = {
            Icon(
                imageVector = Icons.Filled.Groups,
                contentDescription = "team icon",
                tint = MaterialTheme.colorScheme.primary
            )
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